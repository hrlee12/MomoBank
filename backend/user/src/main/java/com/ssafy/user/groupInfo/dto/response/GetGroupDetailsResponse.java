package com.ssafy.user.groupInfo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupDetailsResponse {
    private String groupName;
    private int accountId;
    private boolean status;
    private String accountNumber;
    private int balance;
}
