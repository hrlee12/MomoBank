package com.ssafy.community.groupMember.domain.repository;

import com.ssafy.community.feed.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    Optional<GroupMember> findByGroupMemberIdAndIsDeletedFalse(int groupMemberId);
}
