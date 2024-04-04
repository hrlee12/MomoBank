package com.ssafy.user.member.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BankHomeResponse {
    private BankHomeMemberResponse member;
    private List<BankHomeAccountResponse> account;
    private List<BankHomeGroupResponse> group;


    @QueryProjection
    public BankHomeResponse(BankHomeMemberResponse member, List<BankHomeAccountResponse> account, List<BankHomeGroupResponse> group) {
        this.member = member;
        this.account = account;
        this.group = group;
    }
}