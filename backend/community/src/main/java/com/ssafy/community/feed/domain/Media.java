package com.ssafy.community.feed.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "media")
@Schema(description = "미디어 정보")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "미디어 ID", example = "1")
    private Integer mediaId;

    @Schema(description = "순서", example = "1")
    private Integer sequence;

    @Schema(description = "미디어 타입 (예: 이미지, 비디오)", example = "image")
    private String mediaType;

    @Schema(description = "미디어 URL", example = "http://example.com/image.jpg")
    private String mediaUrl;

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
}