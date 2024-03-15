package com.ssafy.user.group.dto.response;

import java.sql.Date;

public record groupResponseDto(
    String name,
    long monthlyFee,
    Date joinDate,
    int joinMembers,
    Boolean state
) {

}
