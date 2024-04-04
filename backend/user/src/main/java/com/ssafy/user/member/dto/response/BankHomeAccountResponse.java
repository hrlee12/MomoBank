package com.ssafy.user.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankHomeAccountResponse {
    private String bankName;
    private String number;
    private Long balance;

    @QueryProjection
    public BankHomeAccountResponse(String bankName, String number, Long balance) {
        this.bankName = bankName;
        this.number = number;
        this.balance = balance;
    }
}