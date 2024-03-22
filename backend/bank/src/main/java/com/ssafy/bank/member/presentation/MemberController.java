package com.ssafy.bank.member.presentation;


import com.ssafy.bank.common.CommonResponse;
import com.ssafy.bank.member.application.MemberService;
import com.ssafy.bank.member.dto.ReturnPasswordDTO;
import com.ssafy.bank.member.dto.request.JoinRequest;
import com.ssafy.bank.member.dto.request.PasswordUpdateRequest;
import com.ssafy.bank.member.dto.request.SendNewPasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "멤버")
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




    @Operation(summary = "비밀번호 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 완료"),
            @ApiResponse(responseCode = "400", description = "현재 비밀번호 불일치")
    })
    @PutMapping("/passwords")
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
    @PutMapping("/passwords")
    public ResponseEntity sendNewPassword(@RequestBody SendNewPasswordRequest request) {

        ReturnPasswordDTO response = memberService.sendNewPassword(request.getId(), request.getPhoneNumber());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "임시비밀번호 저장", response);
    }




}


