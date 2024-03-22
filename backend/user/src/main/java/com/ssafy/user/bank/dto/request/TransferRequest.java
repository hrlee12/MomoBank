package com.ssafy.user.bank.dto.request;

public record TransferRequest(
    int fromAccountId,
    int toAccountId,
    long amount,
    String description
) {

}
