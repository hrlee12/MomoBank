package com.ssafy.community.report.domain.entity;

import com.ssafy.community.feed.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "member_contributions")
@Schema(description = "회원 기여도 정보")
public class MemberContributions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "기여도 ID", example = "1")
    private Integer memberContributionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "사용자 ID", example = "1")
    private Member member;

    @Schema(description = "연체 일자", example = "0")
    private Integer overdueDate;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "업데이트 일자", example = "2023-03-16T12:00:00")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @Schema(description = "리포트 ID", example = "1")
    private Reports report;
}