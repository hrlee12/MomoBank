package com.ssafy.user.member.repository;

import com.ssafy.user.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByPhoneNumber(String phoneNumber);
}
