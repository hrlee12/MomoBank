package com.ssafy.user.member.controller;


import com.ssafy.user.member.dto.request.TestRequest;
import com.ssafy.user.member.dto.response.*;
import com.ssafy.user.member.dto.request.*;
import com.ssafy.user.member.entity.Member;
import com.ssafy.user.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@Tag(name = "멤버 api")
@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {


    private MemberRepository memberRepository;

    @PostMapping("/phone-verification/code")
    @Operation(summary = "휴대폰 인증번호 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증코드 생성 성공"),
            @ApiResponse(responseCode = "409", description = "이미 가입된 전화번호"),
            @ApiResponse(responseCode = "502", description = "sms를 보내는 과정에서 문제 발생")
    })
    public ResponseEntity makeVerificationCode(@RequestBody MakeVerificationCodeRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/phone-verification/verify")
    @Operation(summary = "휴대폰 인증번호 검증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 일치",
                    content = {@Content(schema = @Schema(implementation=VerifyCodeResponse.class))}),
            @ApiResponse(responseCode = "400", description = "인증번호 불일치")
    })
    public ResponseEntity verifyCode(@RequestBody VerifyCodeRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    @Operation(summary = "회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 완료"),
            @ApiResponse(responseCode = "409", description = "중복되는 아이디"),
            @ApiResponse(responseCode = "401", description = "전화번호 인증 토큰 유효하지 않음")
    })
    public ResponseEntity join(@RequestBody JoinRequest request) {
// 로직 구현
        Member member = Member.builder()
                .id("ssafy")
                .fcmToken("123")
                .birthDate(LocalDateTime.now())
                .password("1234")
                .phoneNumber("010-1234-5678")
                .name("싸피")
                .build();

        memberRepository.save(member);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", responses = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = BankHomeResponse.class))}),
            @ApiResponse(responseCode = "409", description = "아이디 또는 비밀번호 불일치")
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
            @ApiResponse(responseCode = "409", description = "일치하는 id 없음")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity getUserInfo(@PathVariable String memberId) {
// 로직 구현
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비밀번호 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "409", description = "현재 비밀번호 불일치")
    })
    @PatchMapping("/passwords")
    public ResponseEntity updatePassword(@RequestBody PasswordUpdateRequest request) {
// 로직 구현
        return ResponseEntity.ok().build();
    }



    @Operation(summary = "비밀번호 찾기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전화번호로 임시비밀번호 전송"),
            @ApiResponse(responseCode = "409", description = "일치하는 아이디, 전화번호 정보 없음"),
            @ApiResponse(responseCode = "502", description = "sms를 보내는 과정에서 문제 발생")
    })
    @PostMapping("/passwords")
    public ResponseEntity findPassword(@RequestBody PasswordFindRequest request) {
// 로직 구현
        return ResponseEntity.ok().build();
    }



}