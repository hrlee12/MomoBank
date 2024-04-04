package com.ssafy.user.groupInfo.dto.request;

public record CreateNewGroupRequest(
    int memberId,
    String description,
    String groupName,
    int myAccountId,
    int accountId
) {

}
