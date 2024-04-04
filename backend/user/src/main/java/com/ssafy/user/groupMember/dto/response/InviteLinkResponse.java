package com.ssafy.user.groupMember.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InviteLinkResponse {
    private String inviteLink;
}
