package com.ssafy.user.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {

    @Schema(description = "상태코드")
    private int status;
    @Schema(description = "메시지")
    private String message;
    @Schema(description = "데이터")
    private T data;

}
