package com.ssafy.user.member.dto.kafka;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class InsertGroupMemberVO {
    private String memberId;
    private int groupId;
}
