package com.ssafy.user.member.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MemberResponse {
    @Schema(description = "test", defaultValue = "hi")
    private String test;
}
