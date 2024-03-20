package com.ssafy.bank.account.dto.response;

public record AccountResponse(
    String productType,
    String productName,
    String accountNum,
    long balance

) {

}
