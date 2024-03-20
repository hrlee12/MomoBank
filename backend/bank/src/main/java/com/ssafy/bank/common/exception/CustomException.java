package com.ssafy.bank.common.exception;

import com.ssafy.bank.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    ErrorCode errorCode;
}