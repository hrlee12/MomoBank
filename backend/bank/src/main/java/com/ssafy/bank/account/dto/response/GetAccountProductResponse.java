package com.ssafy.bank.account.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAccountProductResponse {

    private int accountProductId;
    private String accountPRoductName;
    private String bankName;
    private String description;
    private float interestRate;

    @QueryProjection
    public GetAccountProductResponse(int accountProductId, String accountPRoductName, String bankName,
        String description, float interestRate) {
        this.accountProductId = accountProductId;
        this.accountPRoductName = accountPRoductName;
        this.bankName = bankName;
        this.description = description;
        this.interestRate = interestRate;
    }
}
