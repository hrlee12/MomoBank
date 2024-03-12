package com.ssafy.community.report.entity;

import com.ssafy.community.feed.entity.GroupInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reports")
@Schema(description = "리포트 정보")
public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "리포트 ID", example = "1")
    private Integer reportId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @Schema(description = "그룹 ID", example = "1")
    private GroupInfo groupInfo;

    @Schema(description = "리포트 타입", example = "Monthly")
    private String reportType;

    @Schema(description = "년도", example = "2023")
    private Integer year;

    @Schema(description = "월", example = "3")
    private Integer month;

    @Schema(description = "내용", example = "3월 모임 리포트")
    private String content;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;
}