package com.ssafy.user.member.dto.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyCodeRequest {
    private String phoneNumber;
    private String code;
}
