package com.ssafy.user.common.exception;

import com.ssafy.user.common.ErrorCode;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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


    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode error, MethodArgumentNotValidException e) {
        Map<String, String> details = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> details.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity
                .status(error.getStatus())
                .body(ErrorResponse.builder()
                        .status(error.getStatus().value())
                        .message(e.getMessage())
                        .body(details.toString())
                        .build());


    }

}
