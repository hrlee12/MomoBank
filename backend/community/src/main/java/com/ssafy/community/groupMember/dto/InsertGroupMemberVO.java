package com.ssafy.community.groupMember.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class InsertGroupMemberVO {
    private String memberId;
    private int groupId;
}