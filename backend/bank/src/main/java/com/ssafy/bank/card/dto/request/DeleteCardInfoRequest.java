package com.ssafy.bank.card.dto.request;

public record DeleteCardInfoRequest(
    int memberId,
    int cardId
) {

}
