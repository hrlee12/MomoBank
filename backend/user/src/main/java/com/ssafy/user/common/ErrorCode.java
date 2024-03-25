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
    ALREADY_JOINED_ID(CONFLICT, "이미 가입된 아이디"),
    PROBLEM_DURING_SENDING_SMS(BAD_GATEWAY, "sms를 보내는 과정에서 문제 발생"),
    INCORRECT_CERTIFICATION_INFO(BAD_REQUEST, "인증정보 불일치"),
    EXPIRED_CERTIFICATION(BAD_REQUEST, "존재하지 않는 인증정보. 휴대전화 번호가 정확한지 확인 요망"),
    NO_MEMBER_INFO(BAD_REQUEST, "제공된 정보와 일치하는 회원 정보 없음"),
    INCORRECT_PASSWORD(BAD_REQUEST, "현재 비밀번호 불일치"),
    NO_MEMBER_TO_UPDATE_FCM_TOKEN(BAD_REQUEST, "fcm 토큰을 갱신할 회원 정보 없음"),
    NO_SUCH_ACCOUNT(FORBIDDEN, "존재하지 않는 계좌입니다."),
    NO_SUCH_MEMBER(FORBIDDEN, "존재하지 않는 회원입니다."),
    NO_SUCH_ACCOUNT_PRODUCT(FORBIDDEN, "존재하지 않는 계좌 상품입니다."),
    NO_SUCH_CARD_PRODUCT(FORBIDDEN, "존재하지 않는 카드 상품입니다."),
    NO_SUCH_CARD_INFO(FORBIDDEN, "존재하지 않는 카드입니다."),
    NO_SUCH_GROUP_INFO(FORBIDDEN, "존재하지 않는 모임입니다."),
    DELETED_ACCOUNT(FORBIDDEN, "이미 삭제 된 계좌입니다."),
    DELETED_MEMBER(FORBIDDEN, "이미 삭제 된 회원입니다."),
    DELETED_ACCOUNT_PRODUCT(FORBIDDEN, "이미 삭제 된 계좌 상품입니다."),
    DELETED_CARD_PRODUCT(FORBIDDEN, "이미 삭제 된 카드 상품입니다."),
    DELETED_CARD_INFO(FORBIDDEN, "이미 삭제 된 카드입니다."),
    DELETED_GROUP_INFO(FORBIDDEN, "이미 삭제 된 모임입니다."),
    YET_TO_BE_DELETED(FORBIDDEN, "잔액이 남아 계좌를 삭제할 수 없습니다.");
    private final HttpStatus status;
    private final String message;
}
