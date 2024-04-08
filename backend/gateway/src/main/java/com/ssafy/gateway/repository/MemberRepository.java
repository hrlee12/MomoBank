package com.ssafy.gateway.repository;

import com.ssafy.gateway.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
