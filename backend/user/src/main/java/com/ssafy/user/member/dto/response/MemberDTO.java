package com.ssafy.user.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.user.bank.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class MemberDTO {
    @Schema(description = "아이디")
    private String id;

    @Schema(description = "이름")
    private String name;
    @Schema(description = "휴대폰번호")
    private String phoneNumber;
    @Schema(description = "생년월일")
    private LocalDateTime birthDate;
    @Schema(description = "가입일")
    private LocalDateTime registrationDate;


    @QueryProjection
    public MemberDTO(String id, String name, String phoneNumber, LocalDateTime birthDate, LocalDateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
    }
}
