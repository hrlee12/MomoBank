package com.ssafy.user.groupInfo.domain.repository;

import com.ssafy.user.groupInfo.domain.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupInfoRepository extends JpaRepository<GroupInfo, Integer>, GroupInfoRepositoryCustom {

    Optional<GroupInfo> findByGroupIdAndIsDeletedFalse(int groupId);
}
