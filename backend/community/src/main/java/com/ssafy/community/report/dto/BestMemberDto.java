package com.ssafy.community.report.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BestMemberDto {
    MemberIdName bestMember;
    String reason;
}


