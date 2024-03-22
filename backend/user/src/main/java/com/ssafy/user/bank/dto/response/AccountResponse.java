package com.ssafy.user.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private int accountId;
    private String productType;
    private String productName;
    private String accountNumber;
    private long balance;
    private int cardId;
}
