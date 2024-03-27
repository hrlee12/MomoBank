package com.ssafy.user.member.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BankHomeMemberResponse {
    @Schema(defaultValue = "아이디")
    private String id;
    @Schema(defaultValue = "이름")
    private String name;
}
