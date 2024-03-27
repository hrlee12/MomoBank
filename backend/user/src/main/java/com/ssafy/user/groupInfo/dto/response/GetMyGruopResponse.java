package com.ssafy.user.groupInfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.sql.Date;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class GetMyGruopResponse {

    private int groupId;
    private String name;
    private long monthlyFee;
    private int joinMembers;
    private Boolean status;

    @QueryProjection
    public GetMyGruopResponse(int groupId, String name, long monthlyFee,
        int joinMembers,
        Boolean state) {
        this.groupId = groupId;
        this.name = name;
        this.monthlyFee = monthlyFee;
        this.joinMembers = joinMembers;
        this.status = state;
    }
}