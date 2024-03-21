package com.ssafy.bank.member.presentation;


import com.ssafy.bank.common.CommonResponse;
import com.ssafy.bank.member.application.MemberService;
import com.ssafy.bank.member.dto.request.JoinRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/join")
    @Operation(summary = "회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 완료"),
            @ApiResponse(responseCode = "409", description = "중복되는 아이디"),
            @ApiResponse(responseCode = "401", description = "전화번호 인증 토큰 유효하지 않음")
    })
    public ResponseEntity join(@RequestBody JoinRequest request) throws Exception {
// 로직 구현
        memberService.join(request);

        return CommonResponse.toResponseEntity(HttpStatus.OK, "회원가입 성공", null);
    }
}


