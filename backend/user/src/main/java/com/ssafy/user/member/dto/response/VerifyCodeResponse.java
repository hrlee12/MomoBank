package com.ssafy.user.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyCodeResponse {
    @Schema(description = "휴대전화 인증 토큰")
    private String authToken;
}
