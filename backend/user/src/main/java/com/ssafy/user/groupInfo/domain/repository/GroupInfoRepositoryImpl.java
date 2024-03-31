package com.ssafy.user.groupInfo.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.domain.QAccount;
import com.ssafy.user.bank.domain.QTransfer;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupMember.domain.QGroupMember;
import com.ssafy.user.member.domain.QMember;
import lombok.RequiredArgsConstructor;
import com.querydsl.core.types.dsl.Expressions;
import com.ssafy.user.budget.domain.QBudget;
import com.ssafy.user.groupInfo.dto.response.GetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.GroupResponse;
import com.ssafy.user.groupInfo.dto.response.QGetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.QGroupResponse;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupInfoRepositoryImpl implements GroupInfoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QGroupInfo group = QGroupInfo.groupInfo;
    private QGroupMember groupMember = QGroupMember.groupMember;
    private QMember member = new QMember("member");
    private QMember accountMember = new QMember("accountMember");
    private QAccount account = QAccount.account;
    private QTransfer fromTransfer = new QTransfer("fromTransfer");
    private QTransfer toTransfer = new QTransfer("toTransfer");


    public List<GetMyGruopResponse> findGroupInfoResponseByMember(int memberId) {
        QBudget budget = QBudget.budget;
        QGroupInfo groupInfo = QGroupInfo.groupInfo;
        QGroupMember groupMember = QGroupMember.groupMember;

        return queryFactory
            .select(new QGetMyGruopResponse(
                groupInfo.groupInfoId,
                groupInfo.groupName,
                budget.monthlyFee.sum(),
                groupInfo.groupMembers.size(),
                Expressions.constant(true)))
            .from(groupInfo)
            .leftJoin(groupInfo.groupMembers, groupMember)
            .leftJoin(groupInfo.budgets, budget)
            .fetch();
    }

    public GroupResponse findGroupResponseByGroup(int groupId, int memberId) {
        QBudget budget = QBudget.budget;
        QGroupInfo groupInfo = QGroupInfo.groupInfo;
        QGroupMember groupMember = QGroupMember.groupMember;

        return queryFactory
            .select(new QGroupResponse(
                groupInfo.groupName,
                groupInfo.description,
                groupInfo.account.balance.subtract(budget.currentMoney.sum()),
                groupMember.totalFee,
                budget.monthlyFee.sum().coalesce(0L),
                groupInfo.account.balance,
                groupInfo.groupMembers.size()
            ))
            .from(groupInfo).groupBy(groupInfo.groupInfoId)
            .leftJoin(groupInfo.groupMembers, groupMember)
            .leftJoin(groupInfo.budgets, budget)
            .where(groupInfo.groupInfoId.eq(groupId),
                groupMember.groupMemberId.eq(memberId))
            .fetchOne();
    }



    public GroupInfo getGroupInfoById(int groupInfoId){
        return queryFactory.select(group)
                .from(group)
                .where(group.groupInfoId.eq(groupInfoId).and(group.isDeleted.eq(false)))
                .join(group.account, account)
                .fetchJoin()
                .where(account.isDeleted.eq(false))
                .join(account.member, accountMember)
                .fetchJoin()
                .where(accountMember.isDeleted.eq(false))
                .fetchOne();
    }
}
