package com.ssafy.bank.common;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_SUCH_ACCOUNT(BAD_REQUEST, "존재하지 않는 계좌"),
    NO_SUCH_MEMBER(BAD_REQUEST, "존재하지 않는 회원"),
    NO_SUCH_ACCOUNT_PRODUCT(BAD_REQUEST, "존재하지 않는 계좌 상품"),
    DELETED_ACCOUNT(BAD_REQUEST, "이미 삭제 된 계좌"),
    DELETED_MEMBER(BAD_REQUEST, "이미 삭제 된 회원"),
    DELETED_ACCOUNT_PRODUCT(BAD_REQUEST, "이미 삭제 된 계좌 상품"),
    ALREADY_JOINED_PHONE_NUMBER(CONFLICT, "이미 가입된 전화번호"),
    PROBLEM_DURING_SENDING_SMS(BAD_GATEWAY, "sms를 보내는 과정에서 문제 발생");


    private final HttpStatus status;
    private final String message;
}
