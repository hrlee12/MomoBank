package com.ssafy.user.bank.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchToAccountResponse {
    private int accountId;
    private String name;

    @QueryProjection
    public SearchToAccountResponse(int accountId, String name) {
        this.accountId = accountId;
        this.name = name;
    }
}
