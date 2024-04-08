package com.ssafy.user.bank.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountProductListResponse {

    private List<GetAccountProductListPerTypeResponse> allAccountProducts;
}
