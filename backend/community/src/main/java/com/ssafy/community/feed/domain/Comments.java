package com.ssafy.community.feed.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "comments")
@Schema(description = "댓글 정보")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "댓글 ID", example = "1")
    private Integer commentId;

    @Schema(description = "댓글 내용", example = "정말 멋진 사진이네요!")
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
    @JoinColumn(name = "feed_id")
    @Schema(description = "소속 피드 ID", example = "1")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "group_member_id")
    @Schema(description = "댓글 작성자 ID", example = "1")
    private GroupMember groupMember;
}