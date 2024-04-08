package com.ssafy.user.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountProductResponse {

    private int accountProductId;
    private String accountProductName;
    private String bankName;
    private String description;
    private float interestRate;
}
