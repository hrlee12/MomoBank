package com.ssafy.user.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificationTokenResponse {
    @Schema(description = "휴대전화/초대링크 인증 토큰")
    private String authToken;
}
