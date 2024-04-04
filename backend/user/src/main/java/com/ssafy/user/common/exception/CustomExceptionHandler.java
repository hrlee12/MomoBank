package com.ssafy.user.common.exception;

import com.ssafy.user.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorResponse>  handleApiException(ApiException e) {
        return ErrorResponse.toResponseEntity(e.getErrorResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e){
        return ErrorResponse.toResponseEntity(ErrorCode.NOT_VALID_ARGUMENT, e);
    }
}

