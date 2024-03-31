package com.ssafy.user.bank.dto.request;

public record CreateAccountRequest(
    int memberId,
    int accountProductId
) {

}

