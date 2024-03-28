package com.ssafy.user.groupMember.domain.repository;

import com.ssafy.user.groupMember.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
    Optional<List<Invite>> findByExpireDateAfterAndGroup_GroupInfoIdAndIsDeletedFalse(LocalDateTime date, int groupId);

}
