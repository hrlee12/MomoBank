package com.ssafy.user.bank.dto.request;

public record CreateCardInfoRequest(
    int memberId,
    int accountId,
    int cardProductId
) {

}

