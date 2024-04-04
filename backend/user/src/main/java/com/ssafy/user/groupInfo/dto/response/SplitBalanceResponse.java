package com.ssafy.user.groupInfo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SplitBalanceResponse {
    private long balance;
    private int totalPeople;
}
