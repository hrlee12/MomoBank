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
import java.util.*;

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




    // 전화번호 인증코드 생성
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




    // 회원가입 시, 전화번호 인증코드 검증하고 인증토큰 발급
    public String getVerificationToken(String code, String phoneNumber) throws Exception {

        // 인증코드 검증
        verifyCode(code, phoneNumber);

        // 전화번호 인증 토큰 생성
        String secretKey = getRandomKey();

        // (전화번호 : 시크릿 키) 저장
        // 만료시간을 지정하기 위해 레디스에 저장.
        redisUtil.setValues(phoneNumber, aesEncrypt(secretKey), Duration.ofSeconds(20 * 60));

        return hashEncrypt(phoneNumber, secretKey);
    }





    // 전화번호 수정 시에, 인증코드 검증하고 수정.
    public void updatePhoneNumber(String id, String phoneNumber, String code) throws Exception {

        // 인증코드 검증
        verifyCode(code, phoneNumber);


        // 전화번호 변경
        Map<String, String> request = new HashMap<>();

        request.put("id", id);
        request.put("newPhoneNumber", phoneNumber);

        // 전화번호 수정
        try {
            ResponseEntity response = restTemplateUtil.send(bankUrl + "/member/phone-numbers", HttpMethod.PUT, request);
        } catch (HttpClientErrorException  e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
    }






    // 회원가입
    @Transactional
    public void join(JoinRequest request) throws Exception {

        // 전화번호 인증토큰 검증
        verifyToken(request.getAuthToken(), request.getPhoneNumber());

        // 회원 정보 저장하기
        try {
            ResponseEntity response = restTemplateUtil.send(bankUrl + "/member/join", HttpMethod.POST, request);
        } catch (HttpClientErrorException  e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }

        // fcm토큰은 계정계에 저장하지 않음.
        // -> 채널계에서 따로 저장해주기
        Member member = memberRepositoryCustom.findMemberById(request.getId());

        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_TO_UPDATE_FCM_TOKEN);

        member.changeFcmToken(request.getFcmToken());
    }






    // 임시 비밀번호 발급
    @Transactional
    public void sendNewPassword(SendNewPasswordRequest request) {

        // 아이디, 기존 비밀번호로 회원 찾기
        MemberToCheckDTO member = memberRepositoryCustom.findMemberToCheckDtoByIdAndPhoneNumber(request.getId(), request.getPhoneNumber());

        if (member == null) {
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);
        }

        // 랜덤 패스워드 생성
        String newPassword = getRandomPassword();


        String message = "임시 비밀번호는 [" + newPassword + "] 입니다.";

        // sms 발송
        sendSms(request.getPhoneNumber(), message);


        Map<String, String> toBankRequest = new HashMap<>();

        toBankRequest.put("id", request.getId());
        toBankRequest.put("phoneNumber", request.getPhoneNumber());
        toBankRequest.put("newPassword", BCrypt.hashpw(newPassword, BCrypt.gensalt()));

        // 임시 비밀번호 저장
        try {
            restTemplateUtil.send(bankUrl + "/member/temporary-passwords", HttpMethod.PUT, toBankRequest);
        } catch (HttpClientErrorException  e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
    }






    // 비밀번호 변경
    public void updatePassword(PasswordUpdateRequest request)  {

        try {
            ResponseEntity response = restTemplateUtil.send(bankUrl + "/member/passwords", HttpMethod.PUT, request);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
    }





    @Transactional
    public void updateFcmToken(String id, String fcmToken) {

        Member member = memberRepositoryCustom.findMemberById(id);

        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_TO_UPDATE_FCM_TOKEN);



        member.changeFcmToken(fcmToken);
    }




    // 마이페이지 조회
    public MypageResponse getUserInfo(String id) {
        // 멤버 정보 조회
        MemberDTO member = memberRepositoryCustom.findMemberDtoById(id);

        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);

        // response 만들기
        MypageResponse mypageResponse = MypageResponse.builder()
                .id(member.getId())
                .birthDate(member.getBirthDate().toLocalDate())
                .phoneNumber(member.getPhoneNumber())
                .name(member.getName())
                .registrationDate(member.getRegistrationDate().toLocalDate())
                .build();


        return mypageResponse;
    }






    private void verifyCode(String code, String phoneNumber) throws Exception {

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





    private String getRandomPassword() {
        String alphabet = "abcdefghijknmlopqrstuvwxyz";
        String number = "0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(number.length());
            sb.append(number.charAt(index));
        }

        return sb.toString();
    }





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






    private void sendSms(String toPhoneNumber, String content){
        
        Message message = new Message();

        message.setFrom(fromNumber);
        message.setTo(toPhoneNumber);



        message.setText(content);

        SingleMessageSentResponse smsResponse;

        try {
            // sms 보내기
            smsResponse = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch(Exception e) {
            throw new CustomException(ErrorCode.PROBLEM_DURING_SENDING_SMS);
        }


        // 2000코드 : 잘 접수됨.
        // 아닌 경우
        if (!smsResponse.getStatusCode().equals("2000")){
            throw new CustomException(ErrorCode.PROBLEM_DURING_SENDING_SMS);
        }

    }
