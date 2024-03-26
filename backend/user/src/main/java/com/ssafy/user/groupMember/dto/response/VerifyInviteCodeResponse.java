package com.ssafy.user.groupMember.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VerifyInviteCodeResponse {
    private String authToken;
    private List<AccountDTO> accounts;
}
