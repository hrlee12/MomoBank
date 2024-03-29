package com.ssafy.bank.transfer.dto.request;

public record TransferRequest(
    String to_account,
    String from_account,
    int ammount
) {

}
