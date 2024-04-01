package com.ssafy.community.groupMember.domain.repository;

import com.ssafy.community.feed.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByIdAndIsDeletedFalse(String memberId);
}
