package com.ssafy.user.bank.dto.request;

public record SearchAccountRequest(
    int myAccountId,
    String bankName,
    String accountNumber
) {

}
