package com.ssafy.user.member.controller;


import com.ssafy.user.common.CommonResponse;
import com.ssafy.user.common.util.RestTemplateUtil;
import com.ssafy.user.member.dto.response.*;
import com.ssafy.user.member.dto.request.*;
import com.ssafy.user.member.entity.Member;
import com.ssafy.user.member.repository.MemberRepository;
import com.ssafy.user.common.util.RedisUtil;
import com.ssafy.user.member.repository.querydsl.MemberRepositoryCustom;
import com.ssafy.user.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.catalina.util.RequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@Tag(name = "멤버 api")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepositoryCustom memberRepositoryCustom;
    private final MemberRepository memberRepository;
    private final WebClient client;
    @Value("${bank.url}")
    private String bankUrl;

    @PostMapping("/phone-verification/code")
    @Operation(summary = "휴대폰 인증번호 요청", description = "인증번호는 3분 후에 만료됨")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증코드 보내기 성공"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 전화번호"),
            @ApiResponse(responseCode = "502", description = "sms를 보내는 과정에서 문제 발생")
    })
    public ResponseEntity makeVerificationCode(@RequestBody MakeVerificationCodeRequest request) throws Exception {

        memberService.makeVerificationCode(request.getPhoneNumber());



        return CommonResponse.toResponseEntity(HttpStatus.OK, "인증코드 보내기 성공", null);
    }



    @PostMapping("/phone-verification/verify")
    @Operation(summary = "휴대폰 인증번호 검증", description = "인증이 완료되면 휴대폰 인증이 됐음을 증명하는 토큰 발급. 토큰은 30분 후에 만료됨")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 일치",
                    content = {@Content(schema = @Schema(implementation=VerifyCodeResponse.class))}),
            @ApiResponse(responseCode = "400", description = "인증번호 불일치")
    })
    public ResponseEntity verifyCode(@RequestBody VerifyCodeRequest request) throws Exception {

        String token = memberService.verifyCode(request.getCode(), request.getPhoneNumber());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "인증번호 일치", new VerifyCodeResponse(token));
    }


    @PostMapping("/join")
    @Operation(summary = "회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 완료"),
            @ApiResponse(responseCode = "409", description = "중복되는 아이디"),
            @ApiResponse(responseCode = "401", description = "전화번호 인증 토큰 유효하지 않음")
    })
    public ResponseEntity join(@RequestBody JoinRequest request) throws Exception {
// 로직 구현



        memberService.verifyToken(request.getAuthToken(), request.getPhoneNumber());


            return new RestTemplateUtil().send(bankUrl + "/member/join", HttpMethod.POST, request);
        // bank로 요청 보내기
//        return CommonResponse.toResponseEntity(HttpStatus.OK, "회원가입 성공", null);
    }





    @PostMapping("/login")
    @Operation(summary = "로그인", responses = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BankHomeResponse.class))}),
            @ApiResponse(responseCode = "400", description = "아이디 또는 비밀번호 불일치")
    })
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

// 로직 구현
        return ResponseEntity.ok().build();
    }




    @Operation(summary = "마이페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MypageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "일치하는 id 없음")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity getUserInfo(@PathVariable String memberId) {

        MypageResponse mypageResponse = memberService.getUserInfo(memberId);

        return CommonResponse.toResponseEntity(HttpStatus.OK, "마이페이지 조회 성공", mypageResponse);
    }





    @Operation(summary = "비밀번호 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "400", description = "현재 비밀번호 불일치")
    })
    @PatchMapping("/passwords")
    public ResponseEntity updatePassword(@RequestBody PasswordUpdateRequest request) {

        memberService.updatePassword(request.getId(), request.getCurrentPassword(), request.getNewPassword());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "비밀번호 변경 완료", null);
    }




    @Operation(summary = "임시 비밀번호 발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전화번호로 임시비밀번호 전송"),
            @ApiResponse(responseCode = "400", description = "입력한 정보와 일치하는 회원 정보 없음"),
            @ApiResponse(responseCode = "502", description = "sms를 보내는 과정에서 문제 발생")
    })
    @PostMapping("/passwords")
    public ResponseEntity sendNewPassword(@RequestBody SendNewPasswordRequest request) {

        memberService.sendNewPassword(request.getId(), request.getPhoneNumber());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "전화번호로 임시비밀번호 전송", null);
    }




    @Operation(summary = "fcm 토큰 갱신")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fcm 토큰 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "제공된 정보와 일치하는 회원 정보 없음"),
    })
    @PatchMapping("/fcmToken")
    public ResponseEntity updateFcmToken(@RequestBody RenewFcmTokenRequest request) {

        memberService.updateFcmToken(request.getId(), request.getFcmToken());


        return CommonResponse.toResponseEntity(HttpStatus.OK, "fcm 토큰 갱신 성공", null);
    }



}