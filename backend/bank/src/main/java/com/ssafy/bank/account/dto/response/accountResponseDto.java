package com.ssafy.bank.account.dto.response;

public record accountResponseDto(
    String productType,
    String productName,
    String accountNum,
    long balance

) {

}
