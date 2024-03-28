package com.ssafy.user.bank.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.user.bank.domain.Account.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMyAccountResponse {

    private int accountId;
    private String accountProductName;
    private AccountType accountProductType;
    private String accountNumber;
    private long balance;

    @QueryProjection
    public GetMyAccountResponse(int accountId, String accountProductName, AccountType accountProductType,
        String accountNumber, long balance) {
        this.accountId = accountId;
        this.accountProductName = accountProductName;
        this.accountProductType = accountProductType;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
