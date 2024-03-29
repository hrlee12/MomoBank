package com.ssafy.user.groupMember.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountDTO {

    private int accountId;
    private String accountNumber;
    private String accountProductName;
    private String bankName;
    private long balance;

    @QueryProjection
    public AccountDTO(int accountId, String accountNumber, String accountProductName, String bankName, long balance) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountProductName = accountProductName;
        this.bankName = bankName;
        this.balance = balance;
    }
}
