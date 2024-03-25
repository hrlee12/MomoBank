package com.ssafy.user.groupInfo.dto.response;

import com.ssafy.user.groupInfo.domain.GroupInfo;
import java.time.LocalDate;
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

    public static GetGroupDetailsResponse from(GroupInfo groupInfo){
        return new GetGroupDetailsResponse(
            groupInfo.getGroupName(),
            groupInfo.getAccount().getAccountId(),
            true, // status
            groupInfo.getAccount().getAccountNumber(),
            groupInfo.getAccount().getBalance()
        );
    }
}
