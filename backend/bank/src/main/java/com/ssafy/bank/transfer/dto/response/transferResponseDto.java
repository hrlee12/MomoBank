package com.ssafy.bank.transfer.dto.response;

import java.util.Date;

public record transferResponseDto(
    String descipt,
    Date transferTime,
    int ammount,
    long balance
) {

}