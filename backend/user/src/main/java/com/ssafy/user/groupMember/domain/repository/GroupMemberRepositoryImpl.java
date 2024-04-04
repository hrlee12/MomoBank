package com.ssafy.user.groupMember.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.groupMember.domain.QGroupMember;
import com.ssafy.user.member.domain.Member;
import java.util.List;

import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;
import com.ssafy.user.groupMember.dto.response.QGroupMemberDTO;
import com.ssafy.user.member.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryImpl implements  GroupMemberRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private QGroupMember groupMember = QGroupMember.groupMember;
    private QMember member = QMember.member;
    private QGroupInfo group = QGroupInfo.groupInfo;

    @Override
    public List<GroupMemberDTO> getAllGroupMemberDTO(int groupId) {
        return queryFactory.select(new QGroupMemberDTO(groupMember.groupMemberId,
                                                groupMember.name,
                                                groupMember.member.sincerity,
                                                groupMember.role
                                                ))
                .from(groupMember)
                .join(groupMember.member, member)
                .where(group.groupInfoId.eq(groupId)
                        .and(group.isDeleted.eq(false)
                        .and(groupMember.isDeleted.eq(false)
                        .and(member.isDeleted.eq(false)))))
                .fetch();
    }

    @Override
    public Account findAccountFromGroupMemberByMember(Member member, GroupInfo groupInfo){
        QGroupMember qGroupMember = QGroupMember.groupMember;
        return queryFactory
            .select(qGroupMember.account)
            .from(groupMember)
            .where(groupMember.member.eq(member),
                groupMember.groupInfo.eq(groupInfo))
            .fetchOne();
    }

    @Override
    public GroupMember findGroupMemberByMemberAndGroupInfo(Member member, GroupInfo groupInfo){
        QGroupMember groupMember = QGroupMember.groupMember;
        return queryFactory
            .selectFrom(groupMember)
            .where(groupMember.member.eq(member),
                groupMember.groupInfo.eq(groupInfo))
            .fetchOne();
    }

}
