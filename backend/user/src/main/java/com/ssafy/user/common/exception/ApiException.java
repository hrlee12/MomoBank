package com.ssafy.user.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    ErrorResponse errorResponse;
}
