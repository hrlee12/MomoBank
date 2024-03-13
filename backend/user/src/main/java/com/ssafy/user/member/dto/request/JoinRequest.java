package com.ssafy.user.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    private String name;
    private String id;
    private String password;
    private String birthdate;
    private String phoneNumber;
    private String authToken;
}