package com.ssafy.community.groupMember.domain.repository;

import com.ssafy.community.feed.entity.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.Optional;

public interface GroupInfoRepository extends JpaRepository<GroupInfo, Integer> {
    Optional<GroupInfo> findByGroupInfoIdAndIsDeletedFalse(int groupId);
}
