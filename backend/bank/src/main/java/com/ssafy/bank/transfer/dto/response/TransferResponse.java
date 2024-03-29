package com.ssafy.bank.transfer.dto.response;

import java.util.Date;

public record TransferResponse(
    String descipt,
    Date transferTime,
    int ammount,
    long balance
) {

}