package com.ssafy.bank.account.dto.response;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.AccountProduct;

public record AccountResponse(
    String productType,
    String productName,
    String accountNum,
    long balance

) {

    public static AccountResponse from(Account account, AccountProduct product) {
        return new AccountResponse(
            product.getType().toString(),
            product.getName(),
            account.getAccountNumber(),
            account.getBalance()
        );
    }
}
