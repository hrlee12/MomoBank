package com.ssafy.user.bank.dto.request;

public record SearchAccountRequest(
    String bank,
    String accountNumber
) {

}
