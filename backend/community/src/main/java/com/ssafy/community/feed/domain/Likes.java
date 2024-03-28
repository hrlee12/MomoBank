package com.ssafy.community.feed.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Schema(description = "좋아요 정보")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "좋아요 ID", example = "1")
    private Integer likeId;

    @Column(nullable = false)
    @Schema(description = "생성일", example = "2023-03-15T12:00:00")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    @Schema(description = "소속 피드 ID", example = "1")
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "group_member_id", nullable = false)
    @Schema(description = "좋아요한 사용자 ID", example = "1")
    private GroupMember groupMember;
}
