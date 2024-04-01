package com.ssafy.user.bank.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchAccountResponse {
    private SearchFromAccountResponse from;
    private SearchToAccountResponse to;
}
