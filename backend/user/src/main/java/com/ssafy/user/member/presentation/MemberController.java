package com.ssafy.user.member.presentation;


import com.ssafy.user.common.CommonResponse;
import com.ssafy.user.member.application.PreLoginService;
import com.ssafy.user.member.dto.response.*;
import com.ssafy.user.member.dto.request.*;
import com.ssafy.user.member.domain.repository.MemberRepository;
import com.ssafy.user.member.domain.repository.MemberRepositoryCustom;
import com.ssafy.user.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;


@Tag(name = "멤버 api")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final PreLoginService preLoginService;
    private final MemberService memberService;


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/phone-verification/code")
    @Operation(summary = "휴대폰 인증번호 요청", description = "전화번호 수정 시, 인증 api. 인증번호는 3분 후에 만료됨")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증코드 보내기 성공"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 전화번호"),
            @ApiResponse(responseCode = "502", description = "sms를 보내는 과정에서 문제 발생")
    })
    public ResponseEntity makeVerificationCode(@RequestBody MakeVerificationCodeRequest request) throws Exception {

        preLoginService.makeVerificationCode(request.getPhoneNumber());



        return CommonResponse.toResponseEntity(HttpStatus.OK, "인증코드 보내기 성공", null);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/phone-verification/phone-numbers")
    @Operation(summary = "휴대폰 인증번호 검증 및 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 일치. 전화번호 수정 완료."),
            @ApiResponse(responseCode = "400", description = "인증번호 불일치"),
            @ApiResponse(responseCode = "404", description = "제공된 정보와 일치하는 회원 정보 없음")
    })
    public ResponseEntity updatePhoneNumber(@RequestBody UpdatePhoneNumberRequest request) throws Exception {

        memberService.updatePhoneNumber(request.getId(), request.getPhoneNumber(), request.getCode());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "인증번호 일치. 전화번호 수정 완료", null);
    }






//    @PostMapping("/login")
//    @Operation(summary = "로그인", responses = {
//            @ApiResponse(responseCode = "200", description = "로그인 성공",
//                    content = {@Content(mediaType = "application/json",
//                    schema = @Schema(implementation = BankHomeResponse.class))}),
//            @ApiResponse(responseCode = "400", description = "아이디 또는 비밀번호 불일치")
//    })
//    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//
//// 로직 구현
//        return ResponseEntity.ok().build();
//    }



    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "마이페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MypageResponse.class))}),
            @ApiResponse(responseCode = "404", description = "제공된 정보와 일치하는 회원 정보 없음")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity getUserInfo(@PathVariable String memberId) {

        MypageResponse mypageResponse = memberService.getUserInfo(memberId);

        return CommonResponse.toResponseEntity(HttpStatus.OK, "마이페이지 조회 성공", mypageResponse);
    }




    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "비밀번호 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "400", description = "현재 비밀번호 불일치")
    })
    @PatchMapping("/passwords")
    public ResponseEntity updatePassword(@RequestBody PasswordUpdateRequest request) {

        memberService.updatePassword(request);

        return CommonResponse.toResponseEntity(HttpStatus.OK, "비밀번호 변경 완료", null);
    }





    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "fcm 토큰 갱신")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fcm 토큰 갱신 성공"),
            @ApiResponse(responseCode = "404", description = "제공된 정보와 일치하는 회원 정보 없음"),
    })
    @PatchMapping("/fcmToken")
    public ResponseEntity updateFcmToken(@RequestBody RenewFcmTokenRequest request) {

        memberService.updateFcmToken(request.getId(), request.getFcmToken());


        return CommonResponse.toResponseEntity(HttpStatus.OK, "fcm 토큰 갱신 성공", null);
    }


    @ApiResponse(responseCode = "200", description = "로그아웃 완료")
    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestHeader("X-Authorization-Id") String memberId) throws Exception {

        memberService.logout(memberId);

        return CommonResponse.toResponseEntity(HttpStatus.OK, "로그아웃 완료", null);
    }


}