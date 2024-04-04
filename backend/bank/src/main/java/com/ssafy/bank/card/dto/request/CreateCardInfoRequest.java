package com.ssafy.bank.card.dto.request;

public record CreateCardInfoRequest(
    int memberId,
    int accountId,
    int cardProductId
) {

}
