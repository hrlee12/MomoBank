package com.ssafy.user.budget.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.budget.domain.QBudget;
import com.ssafy.user.budget.dto.response.GetBudgetResponse;
import com.ssafy.user.budget.dto.response.QGetBudgetResponse;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QBudget budget = QBudget.budget;
    private QGroupInfo groupInfo = QGroupInfo.groupInfo;

    @Override
    public List<GetBudgetResponse> findBudgetResponseByGroupId(int groupInfoId) {
        LocalDate today = LocalDate.now();
        return queryFactory
            .select(Projections.constructor(GetBudgetResponse.class,
                budget.budgetId,
                budget.name,
                budget.monthlyFee,
                budget.currentMoney,
                budget.finalMoney,
                budget.monthlyDueDate,
                budget.dueDate,
                Expressions.constant(true)))
            .from(budget)
            .leftJoin(budget.groupInfo, groupInfo)
            .where(groupInfo.groupInfoId.eq(groupInfoId))
            .fetch();
    }
}
