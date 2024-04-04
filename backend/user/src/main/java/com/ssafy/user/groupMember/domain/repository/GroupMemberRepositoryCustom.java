package com.ssafy.user.groupMember.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;

import com.ssafy.user.member.domain.Member;
import java.util.List;

public interface GroupMemberRepositoryCustom {
    List<GroupMemberDTO> getAllGroupMemberDTO(int groupId);
    Account findAccountFromGroupMemberByMember(Member member, GroupInfo groupInfo);
    GroupMember findGroupMemberByMemberAndGroupInfo(Member member, GroupInfo groupInfo);
}
