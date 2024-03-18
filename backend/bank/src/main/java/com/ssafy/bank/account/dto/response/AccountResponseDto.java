package com.ssafy.bank.account.dto.response;

public record AccountResponseDto(
    String productType,
    String productName,
    String accountNum,
    long balance

) {

}
