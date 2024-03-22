package com.ssafy.user.bank.dto.request;

public record CreateCardRequest(
    int memberId,
    int accountId,
    int cardProductId
) {

}
