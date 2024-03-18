package com.ssafy.bank.account.dto.response;

public record ProductResponseDto(
    String productType,
    String productName,
    float interestRate,
    String productDiscript
    ) {

}
