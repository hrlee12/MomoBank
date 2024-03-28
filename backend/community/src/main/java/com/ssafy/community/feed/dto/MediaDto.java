package com.ssafy.community.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "미디어 정보 DTO")
class MediaDto {
    @Schema(description = "미디어 타입", example = "image")
    private String mediaType;

    @Schema(description = "미디어 URL", example = "https://example.com/image.jpg")
    private String mediaUrl;

    // getters and setters
}
