package com.ssafy.user.bank.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.user.bank.domain.Account.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountResponse {

    private int accountId;
    private AccountType accountProductType;
    private String accountProductName;
    private String accountNumber;
    private long balance;

    @QueryProjection
    public AccountResponse(int accountId, AccountType productType, String productName,
        String accountNumber, long balance) {
        this.accountId = accountId;
        this.accountProductType = productType;
        this.accountProductName = productName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
