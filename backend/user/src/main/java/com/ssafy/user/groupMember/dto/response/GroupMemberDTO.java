package com.ssafy.user.groupMember.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberDTO {
    private int id;
    private String name;
    private int sincerity;

    @QueryProjection
    public GroupMemberDTO(int id, String name, int sincerity) {
        this.id = id;
        this.name = name;
        this.sincerity = sincerity;
    }
}
