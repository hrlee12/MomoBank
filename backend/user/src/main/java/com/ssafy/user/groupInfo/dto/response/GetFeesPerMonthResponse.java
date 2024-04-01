package com.ssafy.user.groupInfo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetFeesPerMonthResponse {
    private int month;
    private long amount;

    @QueryProjection
    public GetFeesPerMonthResponse(int month, long amount){
        this.month = month;
        this.amount = amount;
    }
}
