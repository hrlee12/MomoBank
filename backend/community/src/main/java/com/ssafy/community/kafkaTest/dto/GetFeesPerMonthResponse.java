package com.ssafy.community.kafkaTest.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class GetFeesPerMonthResponse {
    private int month;
    private long amount;

    @QueryProjection
    public GetFeesPerMonthResponse(int month, long amount){
        this.month = month;
        this.amount = amount;
    }
}
