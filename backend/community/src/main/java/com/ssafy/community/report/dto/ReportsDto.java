package com.ssafy.community.report.dto;

import com.ssafy.community.feed.entity.GroupInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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