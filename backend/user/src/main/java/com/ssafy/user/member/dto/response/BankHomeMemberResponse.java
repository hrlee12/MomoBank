package com.ssafy.user.member.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankHomeMemberResponse {
    @Schema(defaultValue = "아이디")
    private String id;
    @Schema(defaultValue = "이름")
    private String name;


    @QueryProjection
    public BankHomeMemberResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
