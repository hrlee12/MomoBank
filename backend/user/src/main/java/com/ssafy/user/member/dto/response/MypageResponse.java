package com.ssafy.user.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MypageResponse {

    @Schema(description = "이름")
    private String name;
    @Schema(description = "아이디")
    private String id;
    @Schema(description = "휴대폰번호")
    private String phoneNumber;
    @Schema(description = "생년월일")
    private String birthDate;
    @Schema(description = "가입일")
    private String registrationDate;
}
