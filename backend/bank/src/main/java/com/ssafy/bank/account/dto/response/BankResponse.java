package com.ssafy.bank.account.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankResponse {

    private int bankId;
    private String companyName;

    @QueryProjection
    public BankResponse(int bankId, String companyName){
        this.bankId = bankId;
        this.companyName = companyName;
    }
}
