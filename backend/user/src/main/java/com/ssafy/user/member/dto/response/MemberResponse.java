package com.ssafy.user.member.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class MemberResponse {
    @Schema(description = "test", defaultValue = "hi")
    private String test;
}
