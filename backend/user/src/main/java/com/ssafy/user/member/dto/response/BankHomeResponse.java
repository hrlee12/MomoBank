package com.ssafy.user.member.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BankHomeResponse {
    private BankHomeMemberResponse member;
    private List<BankHomeAccountResponse> account;
    private List<BankHomeGroupResponse> group;

}