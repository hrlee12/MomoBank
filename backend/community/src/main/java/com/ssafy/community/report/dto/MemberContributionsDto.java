package com.ssafy.community.report.dto;

import com.ssafy.community.feed.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Schema(description = "회원 기여도 정보")
public class MemberContributionsDto {

    @Schema(description = "기여도 ID", example = "1")
    private Integer memberContributionId;

    @Schema(description = "사용자 ID", example = "1")
    private Member user;

    @Schema(description = "연체 일자", example = "0")
    private Integer overdueDate;

    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "업데이트 일자", example = "2023-03-16T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "리포트 ID", example = "1")
    private ReportsDto report;
}