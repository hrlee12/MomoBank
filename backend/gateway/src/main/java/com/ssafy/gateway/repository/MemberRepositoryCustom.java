package com.ssafy.gateway.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.gateway.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QMember member = QMember.member;
    QGroupInfo groupInfo = QGroupInfo.groupInfo;
    QGroupMember groupMember = QGroupMember.groupMember;


    public GroupMember findGroupMemberByMemberIdAndGroupId(String memberId, int groupId){
        return queryFactory.select(groupMember)
                .from(member)
                .where(member.id.eq(memberId).and(member.isDeleted.eq(false)))
                .join(member.groupMembers, groupMember)
                .where(groupMember.groupInfo.groupInfoId.eq(groupId).and(groupMember.isDeleted.eq(false)))
                .fetchOne();
    }


    public Member findGroupLeaderByMemberIdAndGroupId(String memberId, int groupId) {
        return queryFactory.select(member)
                .from(groupInfo)
                .where(groupInfo.groupInfoId.eq(groupId).and(groupInfo.isDeleted.eq(false)))
                .join(groupInfo.member, member)
                .where(member.id.eq(memberId).and(member.isDeleted.eq(false)))
                .fetchOne();
    }

}
