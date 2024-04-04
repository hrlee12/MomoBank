package com.ssafy.bank.account.dto.request;

public record DeleteAccountRequest(
    int memberId,
    int accountId,
    String accountPassword
) {

}
