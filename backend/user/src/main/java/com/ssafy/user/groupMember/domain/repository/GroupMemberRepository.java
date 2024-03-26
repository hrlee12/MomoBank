package com.ssafy.user.groupMember.domain.repository;

import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.member.domain.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer>,
    GroupMemberRepositoryCustom {

    List<GroupMember> findByMember(Member member);

    Optional<GroupMember> findByMember_IdAndGroupInfo_GroupId(String memberId, int groupId);
}
