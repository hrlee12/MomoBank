package com.ssafy.community.report.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
//@Entity
@Table(name = "activity_preferences")
@Schema(description = "활동 선호도 정보")
public class ActivityPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "활동 선호도 ID", example = "1")
    private Integer activityPreferenceId;

    @Schema(description = "활동 유형", example = "Reading")
    private String activityType;

    @Schema(description = "좋아요 수", example = "10")
    private Integer likes;

    @Schema(description = "싫어요 수", example = "2")
    private Integer dislikes;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @Schema(description = "리포트 ID", example = "1")
    private Reports report;
}