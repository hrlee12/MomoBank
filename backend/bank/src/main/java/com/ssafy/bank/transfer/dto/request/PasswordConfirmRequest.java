package com.ssafy.bank.transfer.dto.request;

public record PasswordConfirmRequest(
    int accountId,
    String password
) {

}
