package com.ssafy.user.common.firebase;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private String token;
    private String title;
    private String message;

}
