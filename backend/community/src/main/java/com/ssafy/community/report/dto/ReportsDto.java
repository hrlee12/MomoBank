package com.ssafy.community.report.dto;

import com.ssafy.community.feed.entity.GroupInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "리포트 정보")
public class ReportsDto {

    @Schema(description = "리포트 ID", example = "1")
    private Integer reportId;

    @Schema(description = "그룹 ID", example = "1")
    private GroupInfo groupInfo;

    @Schema(description = "리포트 타입", example = "Monthly")
    private String reportType;

    @Schema(description = "년도", example = "2023")
    private Integer reportYear;

    @Schema(description = "월", example = "3")
    private Integer reportMonth;

    @Schema(description = "내용", example = "3월 모임 리포트")
    private String content;

    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;
}