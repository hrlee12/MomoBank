package com.ssafy.community.report.dto;

import com.ssafy.community.feed.domain.GroupInfo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportsDto {
    private GroupInfo groupInfo;
    private Integer reportYear;
    private Integer reportMonth;
    private String content;
    private LocalDateTime createdAt;
    private BestMemberDto bestMemberDto;
}