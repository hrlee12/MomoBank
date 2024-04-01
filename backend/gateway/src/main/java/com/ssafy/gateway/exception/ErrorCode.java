package com.ssafy.gateway.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_JWT_TOKEN(UNAUTHORIZED, "잘못된 형식의 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(UNAUTHORIZED, "어세스 토큰이 만료되었습니다."),
    JWT_EXCEPTION(UNAUTHORIZED, "jwt 토큰 관련 문제가 있습니다."),
    NO_SUCH_MEMBER_SAME_WITH_JWT_INFO(UNAUTHORIZED, "jwt에 담긴 회원 아이디와 일치하는 회원 정보가 없습니다."),
    NO_AUTHORITY(FORBIDDEN, "자원에 접근할 수 있는 권한이 없습니다."),
    NO_AUTHENTICATION(UNAUTHORIZED, "로그인이 필요한 작업입니다."),
    NO_JWT_TOKEN(UNAUTHORIZED, "헤더에 어세스 토큰 정보가 없습니다.");


    private final HttpStatus status;
    private final String message;


}
