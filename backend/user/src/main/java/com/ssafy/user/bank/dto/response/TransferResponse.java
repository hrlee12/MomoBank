package com.ssafy.user.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private String fromAccountNumber;
    private int fromAccountId;
    private String toAccounntName;
    private long amount;
    private String description;
    private long fromAccountBalance;
}
