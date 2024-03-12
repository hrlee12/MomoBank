package com.ssafy.user.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TestResponse {
    @Schema(description = "이름", defaultValue = "testName")
    private String name;
    @Schema(description = "나이", defaultValue = "1")
    private int age;
}
