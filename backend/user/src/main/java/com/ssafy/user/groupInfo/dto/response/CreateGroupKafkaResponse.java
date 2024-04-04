package com.ssafy.user.groupInfo.dto.response;

public record CreateGroupKafkaResponse (
    int groupInfoId,
    int createdBy,
    String description,
    String groupName
) {

}
