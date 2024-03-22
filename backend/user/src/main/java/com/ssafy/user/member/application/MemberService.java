package com.ssafy.user.member.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.ApiException;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.exception.ErrorResponse;
import com.ssafy.user.common.util.RedisUtil;
import com.ssafy.user.common.util.RestTemplateUtil;
import com.ssafy.user.member.dto.request.JoinRequest;
import com.ssafy.user.member.dto.request.PasswordUpdateRequest;
import com.ssafy.user.member.dto.request.SendNewPasswordRequest;
import com.ssafy.user.member.dto.response.MemberDTO;
import com.ssafy.user.member.dto.response.MemberToCheckDTO;
import com.ssafy.user.member.dto.response.MypageResponse;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import com.ssafy.user.member.domain.repository.MemberRepositoryCustom;
import com.ssafy.user.member.dto.response.ReturnPasswordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.kafka.common.config.types.Password;
import org.apache.tomcat.util.buf.HexUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final DefaultMessageService messageService;
    private final MemberRepository memberRepository;
    private final MemberRepositoryCustom memberRepositoryCustom;
    private final RedisUtil redisUtil;
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;

    @Value("${bank.url}")
    private String bankUrl;
    private String alg = "HmacSHA256";
    @Value("${sms.from-number}")
    private String fromNumber;
    @Value("${encrypt.secret-key}")
    private  String aesSecretKey;
    private final IvParameterSpec iv = new IvParameterSpec(new byte[16]);
    private final String aesAlg = "AES/CBC/PKCS5Padding";



    public void makeVerificationCode(String phoneNumber) throws Exception {

        // 이미 회원가입된 전화번호인지 확인
        MemberToCheckDTO member = memberRepositoryCustom.findMemberToCheckDtoByPhoneNumber(phoneNumber);

        if (member != null)
            throw new CustomException(ErrorCode.ALREADY_JOINED_PHONE_NUMBER);


        // 레디스에 해당번호로 생성된 인증번호 있다면 지우기
        if (redisUtil.existKey(phoneNumber))
            redisUtil.deleteValues(phoneNumber);




        // 랜덤한 숫자(6글자) 생성
        String verificationNumber = "";
        Random random = new Random();

        for (int idx = 0; idx < 6; idx++) {
            verificationNumber += random.nextInt(10);
        }

        String message = "인증번호 [" + verificationNumber + "]를 입력하십시오.";
        sendSms(phoneNumber, message);



        // redis에 저장
        redisUtil.setValues(phoneNumber, aesEncrypt(verificationNumber), Duration.ofSeconds(60*3));

        return;
    }



    public String verifyCode(String code, String phoneNumber) throws Exception {

        // 레디스에 저장된 인증정보 가져오기
        String correctCode = redisUtil.getValues(phoneNumber);

        // 없으면 예외처리
        if (correctCode == null)
            throw new CustomException(ErrorCode.EXPIRED_CERTIFICATION);

        correctCode = aesDecrypt(correctCode);

        // 코드가 틀리면 예외처리
        if (!correctCode.equals(code))
            throw new CustomException(ErrorCode.INCORRECT_CERTIFICATION_INFO);

        // 인증된 정보는 제거
        redisUtil.deleteValues(phoneNumber);

        // 전화번호 인증 토큰 생성
        String secretKey = getRandomKey();

        // (전화번호 : 시크릿 키) 저장
        // 만료시간을 지정하기 위해 레디스에 저장.
        // 향후 시크릿키 암호화로 바꾸기
        redisUtil.setValues(phoneNumber, aesEncrypt(secretKey), Duration.ofSeconds(20*60));

        return hashEncrypt(phoneNumber, secretKey);

    }

    public void join(JoinRequest request) throws Exception {

        verifyToken(request.getAuthToken(), request.getPhoneNumber());

        try {
            ResponseEntity response = restTemplateUtil.send(bankUrl + "/member/join", HttpMethod.POST, request);
        } catch (HttpClientErrorException  e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }

    }

//    public void join(JoinRequest request) throws Exception {
//
//        // 토큰 검증
//        verifyToken(request.getAuthToken(), request.getPhoneNumber());
//
//
//        // 이미 가입된 아이디면 예외처리
//        if(memberRepositoryCustom.findMemberToCheckDtoById(request.getId()) != null)
//            throw new CustomException(ErrorCode.ALREADY_JOINED_ID);
//
//        // 멤버 엔티티 생성 및 저장
//        Member member = Member.builder()
//                .id(request.getId())
//                .name(request.getName())
//                .phoneNumber(request.getPhoneNumber())
//                .birthDate(toLocalDateTime(request.getBirthdate()))
//                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
////                .password(request.getPassword())
//                .build();
//
//
//        memberRepository.save(member);
//    }



    @Transactional
    public void sendNewPassword(SendNewPasswordRequest request) {


        ResponseEntity response;

        try {
            response = restTemplateUtil.send(bankUrl + "/member/join", HttpMethod.PUT, request);
        } catch (HttpClientErrorException  e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }

        ReturnPasswordDTO returnPasswordDTO = (ReturnPasswordDTO)response.getBody();

        String newPassword = returnPasswordDTO.getNewPassword();

        String message = "임시 비밀번호는 [" + newPassword + "] 입니다.";

        try {
            // sms 발송
            sendSms(request.getPhoneNumber(), message);
        } catch (CustomException e) {
            try {
                response = restTemplateUtil.send(bankUrl + "/member/join", HttpMethod.PUT, request);
            } catch (HttpClientErrorException  exception) {
                ErrorResponse errorResponse = exception.getResponseBodyAs(ErrorResponse.class);
                throw new ApiException(errorResponse);
            }
        }



    }




//    @Transactional
//    public void sendNewPassword(String id, String phoneNumber) {
//
//        // 회원정보 일치 확인
//        Member member = memberRepositoryCustom.findMemberByIdAndPhoneNumber(id, phoneNumber);
//
//        if (member == null)
//            throw new CustomException(ErrorCode.NO_MEMBER_INFO);
//
//        // 랜덤 패스워드 생성
//        String newPassword = getRandomPassword();
//
//        String message = "임시 비밀번호는 [" + newPassword + "] 입니다.";
//
//        // sms 발송
//        sendSms(phoneNumber, message);
//
//        // 비밀번호 변경
//        member.changePassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
//
//
//    }
    public void updatePassword(PasswordUpdateRequest request)  {

        try {
            ResponseEntity response = restTemplateUtil.send(bankUrl + "/member/passwords", HttpMethod.PUT, request);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
    }



//    @Transactional
//    public void updatePassword(String id, String currentPassword, String newPassword) {
//
//
//        Member member = memberRepositoryCustom.findMemberById(id);
//
//
//        if (member == null)
//            throw new CustomException(ErrorCode.NO_MEMBER_INFO);
//
//        // 비밀번호 일치 여부 확인
//        if (!BCrypt.checkpw(currentPassword, member.getPassword())){
//            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
//        }
//
//        // 비밀번호 변경
//        member.changePassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
//    }


    @Transactional
    public void updateFcmToken(String id, String fcmToken) {

        Member member = memberRepositoryCustom.findMemberById(id);

        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_TO_UPDATE_FCM_TOKEN);



        member.changeFcmToken(fcmToken);
    }

    public MypageResponse getUserInfo(String id) {
        MemberDTO member = memberRepositoryCustom.findMemberDtoById(id);

        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);

        MypageResponse mypageResponse = MypageResponse.builder()
                .id(member.getId())
                .birthDate(member.getBirthDate().toLocalDate())
                .phoneNumber(member.getPhoneNumber())
                .name(member.getName())
                .registrationDate(member.getRegistrationDate().toLocalDate())
                .build();


        return mypageResponse;
    }

    public void verifyToken(String token, String phoneNumber) throws Exception {

        // 예외처리 안되고 메서드를 무사히 빠져나가면 검증 완료

        String secretKey = redisUtil.getValues(phoneNumber);

        if (secretKey == null) {
            throw new CustomException(ErrorCode.EXPIRED_CERTIFICATION);
        }

        secretKey = aesDecrypt(secretKey);


        if (!hashEncrypt(phoneNumber, secretKey).equals(token)){
            throw new CustomException(ErrorCode.INCORRECT_CERTIFICATION_INFO);
        }



    }

    private String getRandomKey() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

//    private String getRandomPassword() {
//        String alphabet = "abcdefghijknmlopqrstuvwxyz";
//        String number = "0123456789";
//
//        SecureRandom random = new SecureRandom();
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < 5; i++) {
//            int index = random.nextInt(alphabet.length());
//            sb.append(alphabet.charAt(index));
//        }
//
//        for (int i = 0; i < 5; i++) {
//            int index = random.nextInt(number.length());
//            sb.append(number.charAt(index));
//        }
//
//        return sb.toString();
//    }


    private String hashEncrypt(String word, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), alg);

        Mac hasher = Mac.getInstance(alg);
        hasher.init(secretKey);
        byte[] hash = hasher.doFinal(word.getBytes());
        String hashed = HexUtils.toHexString(hash);

        return hashed;
    }


    public String aesEncrypt(String s) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesSecretKey.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance(aesAlg);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        byte[] encrypted = cipher.doFinal(s.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String aesDecrypt(String s) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesSecretKey.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance(aesAlg);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(s));

        return new String(decrypted, "UTF-8");
    }


    private void sendSms(String toPhoneNumber, String content) throws CustomException{
        
        Message message = new Message();

        message.setFrom(fromNumber);
        message.setTo(toPhoneNumber);



        message.setText(content);

        SingleMessageSentResponse smsResponse;

        try {
            // sms 보내기
            smsResponse = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch(Exception e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.PROBLEM_DURING_SENDING_SMS);
        }


        // 2000코드 : 잘 접수됨.
        // 아닌 경우
        if (!smsResponse.getStatusCode().equals("2000")){
            throw new CustomException(ErrorCode.PROBLEM_DURING_SENDING_SMS);
        }

    }

//    // String을 LocalDateTime으로 변환
//    private LocalDateTime toLocalDateTime(String date) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        return LocalDateTime.parse(date + " 00:00:00", formatter);
//    }
}
