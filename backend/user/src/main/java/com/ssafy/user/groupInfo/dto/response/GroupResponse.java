package com.ssafy.user.groupInfo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {

    private String name;
    private String description;
    private long availableBalance;
    private long totalFee;
    private int monthlyDueDate;
    private long totalBalance;
    private int members;
}
