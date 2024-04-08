package com.ssafy.bank.card.dto.response;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.AccountProduct;
import com.ssafy.bank.account.dto.response.AccountResponse;
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
    public static CardInfoResponse from(CardInfo cardInfo, String companyName) {
        return new CardInfoResponse(
            cardInfo.getAccount().getAccountId(),
            cardInfo.getCardInfoId(),
            cardInfo.getCardProduct().getCardInfoImg(),
            cardInfo.getCardProduct().getName(),
            cardInfo.getCardInfoNum(),
            cardInfo.getCardProduct().getDescription(),
            companyName
        );
    }
}
