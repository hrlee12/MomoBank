package com.ssafy.user.groupInfo.domain.repository;

import com.ssafy.user.groupInfo.dto.response.GetMyGruopResponse;
import java.util.List;

public interface GroupInfoRepositoryCustom {
    public List<GetMyGruopResponse> findGroupInfoResponseByMember(int memberId);
}
