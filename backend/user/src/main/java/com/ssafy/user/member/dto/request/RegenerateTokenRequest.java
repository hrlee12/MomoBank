package com.ssafy.user.member.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegenerateTokenRequest {
    private String refreshToken;
}
