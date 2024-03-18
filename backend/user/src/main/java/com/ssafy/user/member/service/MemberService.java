package com.ssafy.user.member.service;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.RedisUtil;
import com.ssafy.user.member.entity.Member;
import com.ssafy.user.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ssafy.user.common.ErrorCode.*;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class MemberService {
    private final DefaultMessageService messageService;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;


    private String alg = "HmacSHA256";


    @Value("${sms.from-number}")
    private String fromNumber;
    public MemberService(MemberRepository memberRepository, RedisUtil redisUtil,
                            @Value("${sms.api-key}") String apiKey,
                            @Value("${sms.api-secret-key}") String apiSecretKey,
                            @Value("${sms.url}") String url) {
        this.memberRepository = memberRepository;
        this.redisUtil = redisUtil;
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, url);
    }


    public void makeVerificationCode(String phoneNumber) {

        // 이미 회원가입된 전화번호인지 확인
        Member member = memberRepository.findByPhoneNumber(phoneNumber).orElse(null);

        if (member != null) {
            throw new CustomException(ErrorCode.ALREADY_JOINED_PHONE_NUMBER);
        }

        // 레디스에 해당번호로 생성된 인증번호 있다면 지우기
        if (redisUtil.existKey(phoneNumber)){
            redisUtil.deleteValues(phoneNumber);
        }


        // sms 보내기
        Message message = new Message();

        message.setFrom(fromNumber);
        message.setTo(phoneNumber);

        // 랜덤한 숫자(6글자) 생성
        String verificationNumber = "";
        Random random = new Random();

        for (int idx = 0; idx < 6; idx++) {
            verificationNumber += random.nextInt(10);
        }


        message.setText("인증번호 [" + verificationNumber + "]를 입력하십시오.");

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


        // redis에 저장
        redisUtil.setValues(phoneNumber, verificationNumber, Duration.ofSeconds(60*3));

        return;
    }


    public String verifyCode(String code, String phoneNumber) throws NoSuchAlgorithmException, InvalidKeyException {

        String correctCode = redisUtil.getValues(phoneNumber);

        if (correctCode == null)
            throw new CustomException(ErrorCode.EXPIRED_CODE);

        if (!correctCode.equals(code))
            throw new CustomException(ErrorCode.INCORRECT_VERIFICATION_CODE);

        redisUtil.deleteValues(phoneNumber);

        String secretKey = getRandomKey();

        redisUtil.setValues(phoneNumber, secretKey, Duration.ofSeconds(30*60));

        return encrypt(phoneNumber, secretKey);

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


    private String encrypt(String word, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), alg);

        Mac hasher = Mac.getInstance(alg);
        hasher.init(secretKey);
        byte[] hash = hasher.doFinal(word.getBytes());
        String hashed = HexUtils.toHexString(hash);

        return hashed;
    }

}
