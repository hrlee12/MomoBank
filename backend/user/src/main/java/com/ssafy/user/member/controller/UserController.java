package com.ssafy.user.member.controller;


import com.ssafy.user.common.CommonResponse;
import com.ssafy.user.member.dto.TestRequest;
import com.ssafy.user.member.dto.TestResponse;
import com.ssafy.user.member.dto.response.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "멤버 관련 api")
@RestController
public class UserController {

    @GetMapping("/test")
    @Operation(summary = "스웨거 테스트", description = "테스트임")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TestResponse.class)))
    })
    public ResponseEntity<TestResponse> login(TestRequest req) {
        return ResponseEntity.ok().body(new TestResponse());
    }


    @GetMapping("/hi")
    @Operation(summary = "hi", description = "hi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful opearion",content = @Content(schema = @Schema(implementation = MemberResponse.class)))
    })
    public ResponseEntity<MemberResponse> hi(TestRequest req) {
        return ResponseEntity.ok().body(new MemberResponse());
    }


    @PostMapping("/phone-verification/code")
    @Operation(summary = "휴대폰 인증번호 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 인증코드를 생성함"),
            @ApiResponse(responseCode = "409", description = "해당 전화번호는 이미 가입된 전화번호임"),
            @ApiResponse(responseCode = "502", description = "sms api에서 부적절한 응답을 받았음")
    })
    public ResponseEntity makeVerificationCode() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/phone-verification/verify")
    @Operation(summary = "휴대폰 인증번호 검증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알맞은 인증번호"),
            @ApiResponse(responseCode = "400", description = "알맞지 않은 인증번호임")
    })
    public ResponseEntity verifyCode() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    @Operation(summary = "휴대폰 인증번호 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 회원가입함"),
            @ApiResponse(responseCode = "409", description = "아이디 또는 비밀번호가 불일치함")
    })
    public ResponseEntity joinMember() {
        return ResponseEntity.ok().build();
    }


    

}