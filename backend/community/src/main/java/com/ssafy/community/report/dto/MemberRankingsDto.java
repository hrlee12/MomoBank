package com.ssafy.community.report.dto;

import com.ssafy.community.feed.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Schema(description = "회원 랭킹 정보")
public class MemberRankingsDto {

    @Schema(description = "랭킹 ID", example = "100")
    private Integer rankingId;

    @Schema(description = "사용자 ID", example = "1")
    private Member user;

    @Schema(description = "점수", example = "100")
    private Integer score;

    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "리포트 ID", example = "1")
    private ReportsDto report;
}