package com.ssafy.user.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {
    int accountId;
    int cardId;
    String cardProductImg;
    String cardProductName;
    String cardNumber;
    String cardDescription;
    String bankName;
    String cardCompanyName;
}
