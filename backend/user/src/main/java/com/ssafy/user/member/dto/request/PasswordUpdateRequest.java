package com.ssafy.user.member.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {
    private String id;
    private String currentPassword;
    private String newPassword;
}
