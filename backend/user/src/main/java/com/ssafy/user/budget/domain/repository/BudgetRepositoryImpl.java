package com.ssafy.user.budget.domain.repository;

import com.querydsl.core.types.dsl.Expressions;
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
        return queryFactory
            .select(new QGetBudgetResponse(
                budget.budgetId,
                budget.name,
                budget.finalMoney.add(budget.currentMoney.multiply(-1)).divide(
                    leftCollectionDate(JPAExpressions
                            .select(budget.monthlyDueDate)
                            .from(budget)
                            .fetchOne()
                        , LocalDate.now(),
                        JPAExpressions
                            .select(budget.dueDate)
                            .from(budget)
                            .fetchOne())),
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


    public static int leftCollectionDate(int day, LocalDate today, LocalDate lastDay) {
        int cnt = 0;

        LocalDate tempDate = today.withDayOfMonth(day);

        if (tempDate.isBefore(today)) {
            tempDate = tempDate.plusMonths(1);
        }

        while (!tempDate.isAfter(lastDay)) {
            if (tempDate.getDayOfMonth() == day) {
                cnt++;
            }
            tempDate = tempDate.plusMonths(1);
        }

        return cnt;
    }
}
