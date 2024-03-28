package com.ssafy.user.groupInfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.user.groupMember.domain.GroupMember;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMyGruopResponse {

    private int groupId;
    private String name;
    private long monthlyFee;
    private Date joinDate;
    private int joinMembers;
    private Boolean state;

    @QueryProjection
    public GetMyGruopResponse(GroupMember groupMember){
        this.groupId = groupMember.getGroupInfo().getGroupInfoId();
        this.name = groupMember.getMember().getName();
//        this.monthlyFee =
        this.joinDate = Date.valueOf(groupMember.getCreatedAt().toLocalDate());
//        this.joinMembers =
//        this.state =
    }
}