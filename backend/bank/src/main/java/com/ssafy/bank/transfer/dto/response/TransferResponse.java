package com.ssafy.bank.transfer.dto.response;

import java.util.Date;

public record TransferResponse(
    String fromAccountNumber,
    int fromAccountId,
    String toAccountName,
    long amount,
    String description,
    long fromAccountBalance
) {

}