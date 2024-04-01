package com.ssafy.user.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakeVerificationCodeRequest {
    @Schema(description = "휴대폰 번호", defaultValue = "01012345678")
    private String phoneNumber;
}
