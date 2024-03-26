package com.ssafy.user.groupMember.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinGroupRequest {
    private int groupId;
    private String memberId;
    private int accountId;
    private String authToken;
}
