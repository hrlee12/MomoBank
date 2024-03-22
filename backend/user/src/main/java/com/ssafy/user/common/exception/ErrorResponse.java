package com.ssafy.user.common.exception;

import com.ssafy.user.common.ErrorCode;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .status(e.getStatus().value())
                        .message(e.getMessage())
                        .build());
    }


    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
    }
}
