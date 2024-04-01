package com.ssafy.user.member.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class LoginRequest {
    @NotNull
    private String id;
    @NotNull
    private String password;
    private String fcmToken;
}