package com.ssafy.community.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "좋아요 DTO")
@Getter
public class LikeDto {
    @Schema(description = "좋아요한 사용자 ID", example = "1")
    private Long userId;

    // Constructor, Getters, and Setters
}
