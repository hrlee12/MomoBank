package com.ssafy.bank.common;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_SUCH_ACCOUNT(FORBIDDEN, "존재하지 않는 계좌입니다."),
    NO_SUCH_MEMBER(FORBIDDEN, "존재하지 않는 회원입니다."),
    NO_SUCH_ACCOUNT_PRODUCT(FORBIDDEN, "존재하지 않는 계좌 상품입니다."),
    DELETED_ACCOUNT(FORBIDDEN, "이미 삭제 된 계좌입니다."),
    DELETED_MEMBER(FORBIDDEN, "이미 삭제 된 회원입니다."),
    DELETED_ACCOUNT_PRODUCT(FORBIDDEN, "이미 삭제 된 계좌 상품입니다."),
    YET_TO_BE_DELETED(FORBIDDEN, "잔액이 남아 계좌를 삭제할 수 없습니다."),
    ALREADY_JOINED_PHONE_NUMBER(CONFLICT, "이미 가입된 전화번호"),
    PROBLEM_DURING_SENDING_SMS(BAD_GATEWAY, "sms를 보내는 과정에서 문제 발생");


    private final HttpStatus status;
    private final String message;
}
