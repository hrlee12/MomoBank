package com.ssafy.user.member.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.ApiException;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.exception.ErrorResponse;
import com.ssafy.user.common.util.EncryptUtil;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final MemberRepositoryCustom memberRepositoryCustom;
    private final RedisUtil redisUtil;
    private final RestTemplateUtil restTemplateUtil;
    private final EncryptUtil encryptUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${bank.url}")
    private String bankUrl;






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
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
    }



    // 비밀번호 변경
    public void updatePassword(PasswordUpdateRequest request) {

        Member member = memberRepositoryCustom.findMemberById(request.getId());


        if (member == null)
            throw new CustomException(ErrorCode.NO_MEMBER_INFO);

//         비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())){
                throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }


        request.setNewPassword(passwordEncoder.encode(request.getNewPassword()));
//        request.setNewPassword(request.getNewPassword());





        try {
            ResponseEntity response = restTemplateUtil.send(bankUrl + "/member/passwords", HttpMethod.PUT, request);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
    }


//

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

//        // 레디스에 저장된 인증정보 가져오기
//        String correctCode = (String)redisUtil.getValues(phoneNumber);
//
//        // 없으면 예외처리
//        if (correctCode == null)
//            throw new CustomException(ErrorCode.EXPIRED_CERTIFICATION);
//
//        correctCode = encryptUtil.aesDecrypt(correctCode);
//
//        // 코드가 틀리면 예외처리
//        if (!correctCode.equals(code))
//            throw new CustomException(ErrorCode.INCORRECT_CERTIFICATION_INFO);
//
//        // 인증된 정보는 제거
//        redisUtil.deleteValues(phoneNumber);

    }

}