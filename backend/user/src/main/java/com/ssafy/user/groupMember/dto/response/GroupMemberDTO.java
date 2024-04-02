package com.ssafy.user.groupMember.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.user.groupMember.domain.GroupMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberDTO {
    private int id;
    private String name;
    private int sincerity;
    private GroupMember.memberType role;

    @QueryProjection
    public GroupMemberDTO(int id, String name, int sincerity, GroupMember.memberType role) {
        this.id = id;
        this.name = name;
        this.sincerity = sincerity;
        this.role = role;
    }
}
