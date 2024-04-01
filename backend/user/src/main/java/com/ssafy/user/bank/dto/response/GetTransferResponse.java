package com.ssafy.user.bank.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetTransferResponse {

    private String name;
    private LocalDateTime date;
    private long amount;
    private boolean transferType;
    private long balance;

    @QueryProjection
    public GetTransferResponse
        (String name, LocalDateTime date, long amount, boolean transferType, long balance) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.transferType = transferType;
        this.balance = balance;
    }
}
