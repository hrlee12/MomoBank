package com.ssafy.gateway.exception;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ErrorResponse {

    private int status;
    private String message;
    private String body;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .status(e.getStatus().value())
                        .message(e.getMessage())
                        .build());
    }





}