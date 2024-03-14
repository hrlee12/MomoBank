package com.ssafy.bank.account.dto.response;

public record productResponseDto(
    String productType,
    String productName,
    float interestRate,
    String productDiscript
    ) {

}
