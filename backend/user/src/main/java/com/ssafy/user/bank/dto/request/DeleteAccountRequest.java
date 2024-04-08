package com.ssafy.user.bank.dto.request;

public record DeleteAccountRequest(
    int memberId,
    int accountId,
    String accountPassword

) {

}
