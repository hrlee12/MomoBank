package com.ssafy.user.groupInfo.domain.repository;

import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.dto.response.GetFeesPerYearResponse;
import com.ssafy.user.groupInfo.dto.response.GetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.GroupResponse;
import com.ssafy.user.member.domain.Member;
import java.util.List;

import com.ssafy.user.groupInfo.domain.GroupInfo;

public interface GroupInfoRepositoryCustom {
    public List<GetMyGruopResponse> findGroupInfoResponseByMember(Member member);
    public GroupResponse findGroupResponseByGroup(int groupId, int memberId);
    public List<GetFeesPerYearResponse> GetFeesPerYear(Member member);

    public GroupInfo getGroupInfoById(int groupInfoId);

}
