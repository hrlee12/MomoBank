package com.ssafy.user.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JoinRequest {
    private String name;
    private String id;
    private String password;
    @Schema(description = "생일", defaultValue = "2024-03-19")
    private String birthdate;
    @Schema(description = "휴대폰번호", defaultValue = "01012345678")
    private String phoneNumber;
    private String authToken;
    private String FcmToken;
}