package com.ssafy.user.groupMember.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;

import java.util.List;

public interface GroupMemberRepositoryCustom {
    List<GroupMemberDTO> getAllGroupMemberDTO(int groupId);
}
