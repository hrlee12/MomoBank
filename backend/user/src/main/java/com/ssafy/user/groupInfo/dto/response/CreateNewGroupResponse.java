package com.ssafy.user.groupInfo.dto.response;

import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.dto.request.CreateNewGroupRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewGroupResponse {
    private int groupId;

    public static CreateNewGroupResponse from(GroupInfo groupInfo){
        return new CreateNewGroupResponse(groupInfo.getGroupInfoId());
    }
}
