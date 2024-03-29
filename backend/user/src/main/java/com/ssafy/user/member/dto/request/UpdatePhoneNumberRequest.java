package com.ssafy.user.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePhoneNumberRequest {
    private String id;
    private String code;
    private String phoneNumber;
}
