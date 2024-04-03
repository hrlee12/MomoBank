package com.ssafy.user.groupInfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
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
    private String description;
    private long monthlyFee;
    private int joinMembers;
    private Boolean status;

    @QueryProjection
    public GetMyGruopResponse(int groupId, String name, String description,
        long monthlyFee, int joinMembers) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.monthlyFee = monthlyFee;
        this.joinMembers = joinMembers;
        this.status = true;
    }
}