package com.ssafy.user.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 api")
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Operation(summary = "어세스 토큰 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재발급 성공")
    })
    @PostMapping("/regenerate")
    public ResponseEntity regenerateToken() {
// 로직 구현
        return ResponseEntity.ok().build();
    }

}
