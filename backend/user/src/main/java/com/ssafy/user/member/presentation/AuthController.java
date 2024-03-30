package com.ssafy.user.member.presentation;


import com.ssafy.user.common.CommonResponse;
import com.ssafy.user.member.application.AuthService;
import com.ssafy.user.member.dto.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "인증 api")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @Operation(summary = "어세스 토큰 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재발급 성공")
    })
    @PostMapping("/regenerate")
    public ResponseEntity regenerateToken() {
// 로직 구현
        return ResponseEntity.ok().build();
    }



    @Operation(summary = "로그인", description = "로그인 성공 시, 헤더에 어세스 토큰과 리프레쉬 토큰을 포함시켜 응답한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "제공된 정보와 일치하는 회원 정보 없음")
    })
    @PostMapping("/login")
    public ResponseEntity login (@Valid @RequestBody LoginRequest request) {

        Map<String, String> tokens = authService.login(request);



        return CommonResponse.toResponseEntity(HttpStatus.OK, "로그인 성공.", null, tokens);
    }





}
