package com.ssafy.community.notice.domain;

import com.ssafy.community.feed.domain.GroupInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notice")
@Schema(description = "공지사항 정보")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "공지사항 ID", example = "1")
    @Column(name = "notice_id")
    private Integer noticeId;

    @Schema(description = "제목", example = "새 공지사항")
    private String title;

    @Schema(description = "내용", example = "공지사항 내용입니다.")
    private String content;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "업데이트 일자", example = "2023-03-16T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부", example = "false")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "group_info_id", nullable = false)
    private GroupInfo groupInfo;
}
