package com.ssafy.bank.account.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.bank.account.domain.AccountProduct.AccountType;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAccountProductListResponse {
    private List<GetAccountProductResponse> accountProductList;
    private AccountType accountType;

    @QueryProjection
    public GetAccountProductListResponse(List<GetAccountProductResponse> accountProductList, AccountType accountType){
        this.accountProductList = accountProductList;
        this.accountType = accountType;
    }
}
