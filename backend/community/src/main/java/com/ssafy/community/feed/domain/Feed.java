package com.ssafy.community.feed.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@Entity
@Table(name = "feed")
@Schema(description = "피드 정보")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "피드 ID", example = "1")
    private Integer feedId;

    @Schema(description = "내용", example = "피드 내용입니다.")
    private String content;

    @Schema(description = "좋아요 수", example = "100")
    private Integer likesCount;

    @Schema(description = "댓글 수", example = "50")
    private Integer commentsCount;

    @Column(nullable = false, updatable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "업데이트 일자", example = "2023-03-16T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "삭제 여부", example = "false")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_member_id")
    private GroupMember groupMember;


}
