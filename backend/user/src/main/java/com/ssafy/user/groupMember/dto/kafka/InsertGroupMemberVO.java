package com.ssafy.user.groupMember.dto.kafka;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class InsertGroupMemberVO {
    private String memberId;
    private int groupId;
}
