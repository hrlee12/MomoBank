package com.ssafy.user.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BankHomeAccountResponse {
    private String bankName;
    private String number;
    private Long balance;
}