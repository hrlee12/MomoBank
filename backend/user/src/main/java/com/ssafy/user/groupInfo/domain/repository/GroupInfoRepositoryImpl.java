package com.ssafy.user.groupInfo.domain.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.budget.domain.QBudget;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupInfo.dto.response.GetGroupDetailsResponse;
import com.ssafy.user.groupInfo.dto.response.GetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.GroupResponse;
import com.ssafy.user.groupInfo.dto.response.QGetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.QGroupResponse;
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
                budget.monthlyFee,
                groupInfo.groupMembers.size(),
                Expressions.constant(true)))
            .from(groupInfo)
            .leftJoin(groupInfo.groupMembers, groupMember)
            .where(groupMember.groupMemberId.eq(memberId))
            .leftJoin(groupInfo.budgets, budget)
            .where(budget.groupInfo.eq(groupInfo))
            .fetch();
    }

    public GroupResponse findGroupResponseByGroup(int groupId, int memberId) {
        return queryFactory
            .select(new QGroupResponse(
                groupInfo.groupName,
                groupInfo.description,
                groupInfo.account.balance.subtract(budget.currentMoney.sum()),
                groupMember.totalFee,
                budget.monthlyDueDate, // 가장 가까운 예산 납부 일?
                groupInfo.account.balance,
                groupInfo.groupMembers.size()
                ))
            .from(groupInfo)
            .leftJoin(groupInfo.groupMembers, groupMember)
            .leftJoin(groupInfo.budgets, budget)
            .where(groupInfo.groupInfoId.eq(groupId), groupMember.groupMemberId.eq(memberId))
            .fetchOne();
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
