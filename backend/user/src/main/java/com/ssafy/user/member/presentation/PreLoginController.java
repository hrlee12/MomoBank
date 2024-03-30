package com.ssafy.user.member.presentation;

import com.ssafy.user.common.CommonResponse;
import com.ssafy.user.member.application.MemberService;
import com.ssafy.user.member.application.PreLoginService;
import com.ssafy.user.member.dto.request.JoinRequest;
import com.ssafy.user.member.dto.request.MakeVerificationCodeRequest;
import com.ssafy.user.member.dto.request.SendNewPasswordRequest;
import com.ssafy.user.member.dto.request.VerificationTokenRequest;
import com.ssafy.user.member.dto.response.VerificationTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "로그인 전 멤버 api")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PreLoginController {

    private final PreLoginService preLoginService;

    @PostMapping("/phone-verification/code")
    @Operation(summary = "휴대폰 인증번호 요청", description = "인증번호는 3분 후에 만료됨")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증코드 보내기 성공"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 전화번호"),
            @ApiResponse(responseCode = "502", description = "sms를 보내는 과정에서 문제 발생")
    })
    public ResponseEntity makeVerificationCode(@RequestBody MakeVerificationCodeRequest request) throws Exception {

        preLoginService.makeVerificationCode(request.getPhoneNumber());



        return CommonResponse.toResponseEntity(HttpStatus.OK, "인증코드 보내기 성공", null);
    }



    @PostMapping("/phone-verification/verify")
    @Operation(summary = "휴대폰 인증번호 검증", description = "인증이 완료되면 휴대폰 인증이 됐음을 증명하는 토큰 발급.토큰은 30분 후에 만료됨.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 일치. 토큰 발급",
                    content = {@Content(schema = @Schema(implementation= VerificationTokenResponse.class))}),
            @ApiResponse(responseCode = "400", description = "인증번호 불일치")
    })
    public ResponseEntity getVerificationToken(@RequestBody VerificationTokenRequest request) throws Exception {

        String token = preLoginService.getVerificationToken(request.getCode(), request.getPhoneNumber());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "인증번호 일치. 토큰 발급", new VerificationTokenResponse(token));

    }


    @PostMapping("/join")
    @Operation(summary = "회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 완료"),
            @ApiResponse(responseCode = "409", description = "중복되는 아이디"),
            @ApiResponse(responseCode = "401", description = "전화번호 인증 토큰 유효하지 않음")
    })
    public ResponseEntity join(@RequestBody JoinRequest request) throws Exception {

        preLoginService.join(request);

        return CommonResponse.toResponseEntity(HttpStatus.OK, "회원가입 성공", null);
    }




    @Operation(summary = "임시 비밀번호 발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전화번호로 임시비밀번호 전송"),
            @ApiResponse(responseCode = "404", description = "제공된 정보와 일치하는 회원 정보 없음"),
            @ApiResponse(responseCode = "502", description = "sms를 보내는 과정에서 문제 발생")
    })
    @PatchMapping("/temporary-passwords")
    public ResponseEntity sendNewPassword(@RequestBody SendNewPasswordRequest request) {

        preLoginService.sendNewPassword(request);

        return CommonResponse.toResponseEntity(HttpStatus.OK, "전화번호로 임시비밀번호 전송", null);
    }


}
