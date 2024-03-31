package com.ssafy.bank.transfer.dto.request;

public record TransferRequest(
    int fromAccountId,
    int toAccountId,
    long amount
) {

}
