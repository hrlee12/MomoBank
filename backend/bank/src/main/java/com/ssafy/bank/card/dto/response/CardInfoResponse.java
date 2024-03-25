package com.ssafy.bank.card.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.bank.card.domain.CardInfo;

public record CardInfoResponse(
    int accountId,
    int cardId,
    String cardProductImg,
    String cardProductName,
    String cardNumber,
    String cardDescription,
    String CompanyName
) {

    @QueryProjection
    public CardInfoResponse (CardInfo cardInfo, String companyName){
        this(cardInfo.getAccount().getAccountId(),
            cardInfo.getCardInfoId(),
            cardInfo.getCardProduct().getCardInfoImg(),
            cardInfo.getCardProduct().getName(),
            cardInfo.getCardInfoNum(),
            cardInfo.getCardProduct().getDescription(),
            companyName);
    }
}
