package com.ssafy.community.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "댓글 DTO")
@Getter
@Setter
@Builder
public class CommentDto {
    @Schema(description = "댓글 ID", example = "1")
    private Integer commentId;

    @Schema(description = "댓글 내용", example = "정말 멋진 사진이네요!")
    private String content;

    // Constructor, Getters, and Setters
}
