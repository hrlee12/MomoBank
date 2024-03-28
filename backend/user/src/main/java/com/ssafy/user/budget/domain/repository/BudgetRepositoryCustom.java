package com.ssafy.user.budget.domain.repository;

import com.ssafy.user.budget.dto.response.GetBudgetResponse;
import java.util.List;

public interface BudgetRepositoryCustom {

    public List<GetBudgetResponse> findBudgetResponseByGroupId(int groupInfoId);
}
