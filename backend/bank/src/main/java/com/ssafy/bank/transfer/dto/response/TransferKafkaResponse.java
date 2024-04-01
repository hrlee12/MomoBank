package com.ssafy.bank.transfer.dto.response;

import com.ssafy.bank.account.domain.Account;

public record TransferKafkaResponse(
    int transferId,
    long amount,
    String description,
    long fromBalance,
    long toBalance,
    Account fromAccount,
    Account toAccount
) {

}
