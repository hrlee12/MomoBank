package com.ssafy.community.feed.domain.repository;

import com.ssafy.community.feed.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {

    @Query("SELECT gm.groupInfo.groupId FROM GroupMember gm WHERE gm.groupMemberId = :userId")
    Integer findGroupIdByUserId(Integer userId);
}
