package com.ssafy.user.groupInfo.dto.response;

public record CreateGroupMemberKafkaResponse(
    int groupMemberId,
    int groupInfoId,
    int memberId,
    String totalFee,
    String name,
    String role
) {

}
