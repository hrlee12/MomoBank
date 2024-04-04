package com.ssafy.bank.member.dto.kafka;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class InsertMemberVO {
    private int memberId;
    private String name;
    private String id;
    private String password;
    private String birthdate;
    private String phoneNumber;
}
