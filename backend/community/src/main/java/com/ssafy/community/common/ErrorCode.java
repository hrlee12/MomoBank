package com.ssafy.community.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	ALREADY_DELETED(BAD_REQUEST, "이미 삭제된 값입니다");

	private final HttpStatus status;
	private final String message;
}