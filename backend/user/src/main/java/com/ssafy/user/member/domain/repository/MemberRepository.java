package com.ssafy.user.member.domain.repository;

import com.ssafy.user.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByPhoneNumber(String phoneNumber);
    Optional<Member> findByIdAndIsDeletedFalse(String memberId);

    void deleteById(String memberId);
}
