package com.ssafy.bank.member.domain.repository;

import com.ssafy.bank.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}
