package com.ssafy.user.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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
    NO_SUCH_BUDGET(FORBIDDEN, "존재하지 않는 예산입니다."),
    DELETED_ACCOUNT(FORBIDDEN, "이미 삭제 된 계좌입니다."),
    DELETED_MEMBER(FORBIDDEN, "이미 삭제 된 회원입니다."),
    DELETED_ACCOUNT_PRODUCT(FORBIDDEN, "이미 삭제 된 계좌 상품입니다."),
    DELETED_CARD_PRODUCT(FORBIDDEN, "이미 삭제 된 카드 상품입니다."),
    DELETED_CARD_INFO(FORBIDDEN, "이미 삭제 된 카드입니다."),
    DELETED_GROUP_INFO(FORBIDDEN, "이미 삭제 된 모임입니다."),
    DELETED_BUDGET(FORBIDDEN, "이미 삭제 된 예산입니다."),
    YET_TO_BE_DELETED(FORBIDDEN, "잔액이 남아 계좌를 삭제할 수 없습니다."),
    ERROR_IN_INTERNAL_MICRO_SERVER(INTERNAL_SERVER_ERROR, "안쪽 마이크로 서버의 에러"),
    NO_GROUP_MEMBER_INFO(BAD_REQUEST, "조회되는 그룹 멤버가 없습니다."),
    NO_GROUP_INFO(BAD_REQUEST, "주어진 아이디로 조회되는 그룹이 없습니다. 그룹 아이디를 확인해주세요."),
    NO_INVITE_LINK(BAD_REQUEST, "해당 초대링크가 존재하지 않습니다. 만료된 링크인지 확인해주세요."),
    ALREADY_JOINED_MEMBER(CONFLICT, "해당 모임에 이미 가입한 회원입니다."),
    GROUP_LEADER_CANNOT_RESIGN(BAD_REQUEST, "모임장은 탈퇴할 수 없습니다."),
    NOT_OWN_GROUP_MEMBER(BAD_REQUEST, "해당 회원의 그룹 멤버 정보가 아닙니다."),
    NOT_VALID_ARGUMENT(BAD_REQUEST, "바디의 파라미터가 적절하지 못합니다."),
    INVALID_JWT_TOKEN(UNAUTHORIZED, "잘못된 형식의 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(UNAUTHORIZED, "어세스 토큰이 만료되었습니다."),
    JWT_EXCEPTION(UNAUTHORIZED, "jwt 토큰 관련 문제가 있습니다."),
    NO_SUCH_MEMBER_SAME_WITH_JWT_INFO(UNAUTHORIZED, "jwt에 담긴 회원 아이디와 일치하는 회원 정보가 없습니다."),
    NO_AUTHORITY(FORBIDDEN, "자원에 접근할 수 있는 권한이 없습니다."),
    NO_AUTHENTICATION(UNAUTHORIZED, "로그인이 필요한 작업입니다.");
    private final HttpStatus status;
    private final String message;

    public ErrorCode findErrorCode() {
        return ErrorCode.ALREADY_DELETED;
    }
}
