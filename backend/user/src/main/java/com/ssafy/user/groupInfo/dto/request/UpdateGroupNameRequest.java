package com.ssafy.user.groupInfo.dto.request;

public record UpdateGroupNameRequest(
    int memberId,
    String groupName
) {

}
