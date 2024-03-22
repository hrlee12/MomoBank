package com.ssafy.user.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetMyAccountResponse {

    private int accountId;
    private String accountProductName;
    private String accountProductType;
    private String accountNumber;
    private int balance;
}
