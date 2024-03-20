package com.ssafy.user.member.service;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.RedisUtil;
import com.ssafy.user.member.dto.request.JoinRequest;
import com.ssafy.user.member.dto.response.MemberDTO;
import com.ssafy.user.member.dto.response.MemberToCheckDTO;
import com.ssafy.user.member.dto.response.MypageResponse;
import com.ssafy.user.member.entity.Member;
import com.ssafy.user.member.repository.MemberRepository;
import com.ssafy.user.member.repository.querydsl.MemberRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.tomcat.util.buf.HexUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ssafy.user.common.ErrorCode.*;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private String alg = "HmacSHA256";


    @Value("${sms.from-number}")
    private String fromNumber;





    public void makeVerificationCode(String phoneNumber) {

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
        redisUtil.setValues(phoneNumber, verificationNumber, Duration.ofSeconds(60*3));

        return;
    }



    public String verifyCode(String code, String phoneNumber) throws NoSuchAlgorithmException, InvalidKeyException {

        // 레디스에 저장된 인증정보 가져오기
        String correctCode = redisUtil.getValues(phoneNumber);

        // 없으면 예외처리
        if (correctCode == null)
            throw new CustomException(ErrorCode.EXPIRED_CERTIFICATION);

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
        redisUtil.setValues(phoneNumber, secretKey, Duration.ofSeconds(20*60));

        return encrypt(phoneNumber, secretKey);

    }

    public void join(JoinRequest request) throws NoSuchAlgorithmException, InvalidKeyException {

        // 토큰 검증
        verifyToken(request.getAuthToken(), request.getPhoneNumber());


        // 이미 가입된 아이디면 예외처리
        if(memberRepositoryCustom.findMemberToCheckDtoById(request.getId()) != null)
            throw new CustomException(ErrorCode.ALREADY_JOINED_ID);

        // 멤버 엔티티 생성 및 저장
        Member member = Member.builder()
                .id(request.getId())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(toLocalDateTime(request.getBirthdate()))
                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
//                .password(request.getPassword())
                .build();


        memberRepository.save(member);
    }



    @Transactional
    public void sendNewPassword(String id, String phoneNumber) {

        // 회원정보 일치 확인
        Member member = memberRepositoryCustom.findMemberByIdAndPhoneNumber(id, phoneNumber);

        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);

        // 랜덤 패스워드 생성
        String newPassword = getRandomPassword();

        String message = "임시 비밀번호는 [" + newPassword + "] 입니다.";

        // sms 발송
        sendSms(phoneNumber, message);

        // 비밀번호 변경
        member.changePassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));

    }


    @Transactional
    public void updatePassword(String id, String currentPassword, String newPassword) {


        Member member = memberRepositoryCustom.findMemberById(id);


        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);

        // 비밀번호 일치 여부 확인
        if (!BCrypt.checkpw(currentPassword, member.getPassword())){
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        // 비밀번호 변경
        member.changePassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
    }


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

    private void verifyToken(String token, String phoneNumber) throws NoSuchAlgorithmException, InvalidKeyException {

        // 예외처리 안되고 메서드를 무사히 빠져나가면 검증 완료

        String secretKey = redisUtil.getValues(phoneNumber);

        if (secretKey == null) {
            throw new CustomException(ErrorCode.EXPIRED_CERTIFICATION);
        }

        if (!encrypt(phoneNumber, secretKey).equals(token)){
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


    private String encrypt(String word, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), alg);

        Mac hasher = Mac.getInstance(alg);
        hasher.init(secretKey);
        byte[] hash = hasher.doFinal(word.getBytes());
        String hashed = HexUtils.toHexString(hash);

        return hashed;
    }

    
    private void sendSms(String toPhoneNumber, String content) {
        
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

    // String을 LocalDateTime으로 변환
    private LocalDateTime toLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date + " 00:00:00", formatter);
    }
}
