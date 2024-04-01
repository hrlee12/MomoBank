package com.ssafy.community.report.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "monthly_reports")
@Schema(description = "리포트 정보")
public class MonthlyReports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "report_year")
    private Integer reportYear;

    @Column(name = "report_month")
    private Integer reportMonth;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "best_member_id")
    private Integer bestMemberId;

    @Column(name = "best_member_name")
    private String bestMemberName;

    @Column(name = "best_member_reason")
    private String bestMemberReason;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}