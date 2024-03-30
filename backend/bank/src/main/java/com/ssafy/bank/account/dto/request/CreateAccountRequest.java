package com.ssafy.bank.account.dto.request;

public record CreateAccountRequest(
    int memberId,
    int accountProductId,
    String accountPassword
) {

}
