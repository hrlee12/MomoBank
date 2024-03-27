package com.ssafy.user.groupMember.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupMemberListDTO {
    private List<GroupMemberDTO> members;
}
