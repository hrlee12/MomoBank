package com.ssafy.user.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MypageResponse {

    @Schema(description = "이름")
    private String name;
    @Schema(description = "아이디")
    private String id;
    @Schema(description = "휴대폰번호")
    private String phoneNumber;
    @Schema(description = "생년월일")
    private LocalDate birthDate;
    @Schema(description = "가입일")
    private LocalDate registrationDate;
}
