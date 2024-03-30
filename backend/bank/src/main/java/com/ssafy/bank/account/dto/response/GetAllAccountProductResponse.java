package com.ssafy.bank.account.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAccountProductResponse {
    private List<GetAccountProductListResponse> allAccountProducts;
}
