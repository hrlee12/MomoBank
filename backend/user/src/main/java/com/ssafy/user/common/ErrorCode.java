package com.ssafy.user.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_DELETED(BAD_REQUEST, "이미 삭제된 값"),
    ALREADY_JOINED_PHONE_NUMBER(CONFLICT, "이미 가입된 전화번호"),
    PROBLEM_DURING_SENDING_SMS(BAD_GATEWAY, "sms를 보내는 과정에서 문제 발생");


    private final HttpStatus status;
    private final String message;
}
