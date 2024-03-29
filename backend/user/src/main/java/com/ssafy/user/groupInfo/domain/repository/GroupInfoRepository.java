package com.ssafy.user.groupInfo.domain.repository;

import com.ssafy.user.groupInfo.domain.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupInfoRepository extends JpaRepository<GroupInfo, Integer>, GroupInfoRepositoryCustom {

}
