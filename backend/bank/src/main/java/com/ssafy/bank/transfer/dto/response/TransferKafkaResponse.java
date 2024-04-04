package com.ssafy.bank.transfer.dto.response;

import com.ssafy.bank.account.domain.Account;

public record TransferKafkaResponse(
    int transferId,
    String amount,
    String description,
    String fromBalance,
    String toBalance,
    int fromAccount,
    int toAccount
) {

}
