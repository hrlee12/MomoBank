package com.ssafy.user.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewFcmTokenRequest {
    private String id;
    private String fcmToken;
}
