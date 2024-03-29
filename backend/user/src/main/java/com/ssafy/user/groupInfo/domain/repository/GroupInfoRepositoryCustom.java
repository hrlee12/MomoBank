package com.ssafy.user.groupInfo.domain.repository;

import com.ssafy.user.groupInfo.dto.response.GetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.GroupResponse;
import java.util.List;

import com.ssafy.user.groupInfo.domain.GroupInfo;

public interface GroupInfoRepositoryCustom {
    public List<GetMyGruopResponse> findGroupInfoResponseByMember(int memberId);
    public GroupResponse findGroupResponseByGroup(int groupId, int memberId);

    GroupInfo getGroupInfoById(int groupInfoId);

}
