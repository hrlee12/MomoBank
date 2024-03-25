package com.ssafy.user.groupInfo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupDetailsResponse {
    private String groupName;
    private int accountId;
    private boolean status;
    private String accountNumber;
    private long balance;


}
