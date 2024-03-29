package com.ssafy.bank.account.domain.repository;

import com.ssafy.bank.account.dto.response.GetAccountProductListResponse;
import java.util.List;

public interface AccountRepositoryCustom {
    public List<GetAccountProductListResponse> findProductListByType();
}
