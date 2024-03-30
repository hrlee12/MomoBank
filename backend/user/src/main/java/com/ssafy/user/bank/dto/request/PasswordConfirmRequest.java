package com.ssafy.user.bank.dto.request;

public record PasswordConfirmRequest(
    int accountId,
    String password
) {

}
