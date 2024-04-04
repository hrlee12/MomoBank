package com.ssafy.user.groupInfo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateGroupAndGroupMemberResponse {
    int groupInfoId;
    int createdBy;
    String description;
    String groupName;
    int groupMemberId;
    int memberId;
    String totalFee;
    String name;
    String role;
}


