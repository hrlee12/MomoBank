package com.ssafy.community.feed.domain.repository;

import com.ssafy.community.feed.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {

    @Query("SELECT gm.groupInfo.groupId FROM GroupMember gm WHERE gm.member.memberId = :memberId")
    Integer findGroupIdByMemberId(Integer memberId);


    @Query("SELECT gm FROM GroupMember gm WHERE gm.member.memberId = :memberId")
    GroupMember findGroupMemberByMemberId(Integer memberId);


;
}
