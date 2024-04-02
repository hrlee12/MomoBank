package com.ssafy.user.groupInfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupMember.domain.GroupMember;
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
    private int groupMemberId;
    public static GetGroupDetailsResponse from(GroupInfo groupInfo, GroupMember groupMember){
        return new GetGroupDetailsResponse(
            groupInfo.getGroupName(),
            groupInfo.getAccount().getAccountId(),
            true, // status
            groupInfo.getAccount().getAccountNumber(),
            groupInfo.getAccount().getBalance(),
            groupMember.getGroupMemberId()
        );
    }
}
