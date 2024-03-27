package com.ssafy.user.groupInfo.domain.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.budget.domain.QBudget;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupInfo.dto.response.GetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.QGetMyGruopResponse;
import com.ssafy.user.groupMember.domain.QGroupMember;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupInfoRepositoryImpl implements GroupInfoRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QBudget budget = QBudget.budget;
    private QGroupInfo groupInfo = QGroupInfo.groupInfo;
    private QGroupMember groupMember = QGroupMember.groupMember;

    public List<GetMyGruopResponse> findGroupInfoResponseByMember(int memberId) {
        return queryFactory
            .select(new QGetMyGruopResponse(
                groupInfo.groupInfoId,
                groupInfo.groupName,
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
                groupInfo.groupMembers.size(),
                Expressions.constant(true)))
            .from(groupInfo)
            .leftJoin(groupInfo.groupMembers, groupMember).where(groupMember.groupMemberId.eq(memberId))
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
