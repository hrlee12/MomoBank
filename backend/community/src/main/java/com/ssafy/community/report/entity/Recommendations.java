package com.ssafy.community.report.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "recommendations")
@Schema(description = "추천 정보")
public class Recommendations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "추천 ID", example = "1")
    private Integer recommendationId;

    @Schema(description = "추천 타입", example = "Activity")
    private String type;

    @Schema(description = "추천 내용", example = "독서 모임 추천")
    private String recommendation;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @Schema(description = "리포트 ID", example = "1")
    private Reports report;
}