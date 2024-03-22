package com.ssafy.community.report.domain.entity;

import com.ssafy.community.feed.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "member_rankings")
@Schema(description = "회원 랭킹 정보")
public class MemberRankings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "랭킹 ID", example = "1")
    private Integer rankingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "사용자 ID", example = "1")
    private Users user;

    @Schema(description = "점수", example = "100")
    private Integer score;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @Schema(description = "리포트 ID", example = "1")
    private Reports report;
}