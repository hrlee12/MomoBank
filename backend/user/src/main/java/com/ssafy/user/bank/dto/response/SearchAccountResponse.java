package com.ssafy.user.bank.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchAccountResponse {
    private int accountId;
    private String name;

    @QueryProjection
    public SearchAccountResponse(int accountId, String name) {
        this.accountId = accountId;
        this.name = name;
    }
}
