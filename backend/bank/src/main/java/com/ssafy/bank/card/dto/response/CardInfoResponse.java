package com.ssafy.bank.card.dto.response;

import com.ssafy.bank.card.domain.CardInfo;

public record CardInfoResponse(
    int accountId,
    int cardId,
    String cardProductImg,
    String cardProductName,
    String cardNumber,
    String cardDescription,
    String bankName,
    String cardCompanyName
) {

    public static CardInfoResponse from(CardInfo cardInfo){
        return new CardInfoResponse(
            cardInfo.getAccount().getAccountId(),
            cardInfo.getCardInfoId(),
            cardInfo.getCardProduct().getCardInfoImg(),
            cardInfo.getCardProduct().getName(),
            cardInfo.getCardInfoNum(),
            cardInfo.getCardProduct().getDescription(),
            cardInfo.getCardProduct().getBank().getBankName(),
            cardInfo.getCardProduct().getCard().getCardName()
        );
    }
}
