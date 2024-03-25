package com.ssafy.user.groupMember.domain.repository;

import com.ssafy.user.groupMember.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
}
