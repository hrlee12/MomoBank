package com.ssafy.user.groupInfo.dto.request;

public record UpdateGroupDescriptionRequest(
    int memberId,
    String description
) {

}
