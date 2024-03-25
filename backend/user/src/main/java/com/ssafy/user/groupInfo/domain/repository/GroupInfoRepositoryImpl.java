package com.ssafy.user.groupInfo.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.entity.QAccount;
import com.ssafy.user.bank.entity.QTransfer;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupMember.domain.QGroupMember;
import com.ssafy.user.member.domain.QMember;
import lombok.RequiredArgsConstructor;
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


    public GroupInfo getGroupInfoById(int groupInfoId){
        return queryFactory.select(group)
                .from(group)
                .where(group.groupId.eq(groupInfoId).and(group.isDeleted.eq(false)))
                .join(group.account, account)
                .fetchJoin()
                .where(account.isDeleted.eq(false))
                .join(account.member, accountMember)
                .fetchJoin()
                .where(accountMember.isDeleted.eq(false))
                .fetchOne();
    }
}
