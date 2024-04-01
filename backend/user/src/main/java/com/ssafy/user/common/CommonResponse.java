package com.ssafy.user.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
@Builder
public class CommonResponse<T> {

    private int status;
    private String message;
    private T data;


    public static ResponseEntity<CommonResponse> toResponseEntity(HttpStatus httpStatus, String message, Object data) {
        return ResponseEntity
                .status(httpStatus)
                .body(CommonResponse.builder()
                        .status(httpStatus.value())
                        .message(message)
                        .data(data)
                        .build());

    }

    public static <T> ResponseEntity<CommonResponse> toResponseEntity(HttpStatus httpStatus, String message, Object data, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        header.forEach(headers::add);
        return ResponseEntity
            .status(httpStatus)
            .headers(headers)
            .body(CommonResponse.builder()
                    .status(httpStatus.value())
                    .message(message)
                    .data(data)
                    .build());
    }

}
