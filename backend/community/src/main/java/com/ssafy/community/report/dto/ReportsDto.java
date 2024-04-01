package com.ssafy.community.report.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportsDto {
    private Integer groupId;
    private Integer reportYear;
    private Integer reportMonth;
    private String content;
    private LocalDateTime createdAt;
    private BestMemberDto bestMember;
}