package com.ssafy.user.member.application;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.ApiException;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.exception.ErrorResponse;
import com.ssafy.user.common.util.EncryptUtil;
import com.ssafy.user.common.util.RedisUtil;
import com.ssafy.user.common.util.RestTemplateUtil;
import com.ssafy.user.member.domain.repository.MemberRepositoryCustom;
import com.ssafy.user.member.dto.request.JoinRequest;
import com.ssafy.user.member.dto.request.SendNewPasswordRequest;
import com.ssafy.user.member.dto.response.MemberToCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class PreLoginService {

    private final DefaultMessageService messageService;
    private final MemberRepositoryCustom memberRepositoryCustom;
    private final RedisUtil redisUtil;
    private final RestTemplateUtil restTemplateUtil;
    private final EncryptUtil encryptUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${bank.url}")
    private String bankUrl;
    @Value("${sms.from-number}")
    private String fromNumber;





    // 전화번호 인증코드 생성
    public void makeVerificationCode(String phoneNumber) throws Exception {

        // 이미 회원가입된 전화번호인지 확인
        MemberToCheckDTO member = memberRepositoryCustom.findMemberToCheckDtoByPhoneNumber(phoneNumber);

        if (member != null)
            throw new CustomException(ErrorCode.ALREADY_JOINED_PHONE_NUMBER);


        // 레디스에 해당번호로 생성된 인증번호 있다면 지우기
//        if (redisUtil.existKey(phoneNumber))
//            redisUtil.deleteValues(phoneNumber);


        // 랜덤한 숫자(6글자) 생성
        String verificationNumber = "";
        Random random = new Random();

        for (int idx = 0; idx < 6; idx++) {
            verificationNumber += random.nextInt(10);
        }

        String message = "인증번호 [" + verificationNumber + "]를 입력하십시오.";
        sendSms(phoneNumber, message);

        // redis에 저장
//        redisUtil.setValues(phoneNumber, encryptUtil.aesEncrypt(verificationNumber), Duration.ofSeconds(60 * 3));

        return;
    }







    // 회원가입 시, 전화번호 인증코드 검증하고 인증토큰 발급
    public String getVerificationToken(String code, String phoneNumber) throws Exception {

        // 인증코드 검증
        verifyCode(code, phoneNumber);

        // 전화번호 인증 토큰 생성
        String secretKey = encryptUtil.getRandomKey();

        // (전화번호 : 시크릿 키) 저장
        // 만료시간을 지정하기 위해 레디스에 저장.
//        redisUtil.setValues(phoneNumber, encryptUtil.aesEncrypt(secretKey), Duration.ofSeconds(20 * 60));

        return encryptUtil.hashEncrypt(phoneNumber, secretKey);
    }








    // 회원가입
    @Transactional
    public void join(JoinRequest request) throws Exception {

        // 전화번호 인증토큰 검증
        verifyToken(request.getAuthToken(), request.getPhoneNumber());

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        // 회원 정보 저장하기
        try {
            ResponseEntity response = restTemplateUtil.send(bankUrl + "/member/join", HttpMethod.POST, request);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }


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
//        toBankRequest.put("newPassword", passwordEncoder.encode(newPassword));
        toBankRequest.put("newPassword", newPassword);



        // 임시 비밀번호 저장
        try {
            restTemplateUtil.send(bankUrl + "/member/temporary-passwords", HttpMethod.PUT, toBankRequest);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
    }




    // 전화번호 인증코드 인증
    private void verifyCode(String code, String phoneNumber) throws Exception {

        // 레디스에 저장된 인증정보 가져오기
//        String correctCode = (String)redisUtil.getValues(phoneNumber);
//
//        // 없으면 예외처리
//        if (correctCode == null)
//            throw new CustomException(ErrorCode.EXPIRED_CERTIFICATION);

//        correctCode = encryptUtil.aesDecrypt(correctCode);
//
//        // 코드가 틀리면 예외처리
//        if (!correctCode.equals(code))
//            throw new CustomException(ErrorCode.INCORRECT_CERTIFICATION_INFO);
//
//        // 인증된 정보는 제거
//        redisUtil.deleteValues(phoneNumber);

    }



    // 전화번호 인증 확인 토큰 검증
    private  void verifyToken(String token, String phoneNumber) throws Exception {

        // 예외처리 안되고 메서드를 무사히 빠져나가면 검증 완료

//        String secretKey = (String)redisUtil.getValues(phoneNumber);
//
//        if (secretKey == null) {
//            throw new CustomException(ErrorCode.EXPIRED_CERTIFICATION);
//        }
//
//        secretKey = encryptUtil.aesDecrypt(secretKey);
//
//
//        if (!encryptUtil.hashEncrypt(phoneNumber, secretKey).equals(token)) {
//            throw new CustomException(ErrorCode.INCORRECT_CERTIFICATION_INFO);
//        }
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






    private void sendSms(String toPhoneNumber, String content) {

        Message message = new Message();

        message.setFrom(fromNumber);
        message.setTo(toPhoneNumber);


        message.setText(content);

        SingleMessageSentResponse smsResponse = null;

        try {
            // sms 보내기
            smsResponse = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {

            log.info(e.getMessage());


            throw new CustomException(ErrorCode.PROBLEM_DURING_SENDING_SMS);
        }


        // 2000코드 : 잘 접수됨.
        // 아닌 경우
        if (smsResponse != null && !smsResponse.getStatusCode().equals("2000")) {
            log.info("sms 오류 발생 - code : " + smsResponse.getStatusCode() + " " + smsResponse.getStatusMessage());
            throw new CustomException(ErrorCode.PROBLEM_DURING_SENDING_SMS);
        }
    }


}
