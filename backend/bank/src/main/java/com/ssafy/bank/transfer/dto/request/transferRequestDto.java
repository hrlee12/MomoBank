package com.ssafy.bank.transfer.dto.request;

public record transferRequestDto(
    String to_account,
    String from_account,
    int ammount
) {

}
