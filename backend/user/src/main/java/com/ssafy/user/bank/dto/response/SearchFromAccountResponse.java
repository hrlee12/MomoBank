package com.ssafy.user.bank.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchFromAccountResponse {
    private String myAccountName;
    private long myAccountBalance;

    @QueryProjection
    public SearchFromAccountResponse(String name, long balance) {
        this.myAccountName = name;
        this.myAccountBalance = balance;
    }
}
