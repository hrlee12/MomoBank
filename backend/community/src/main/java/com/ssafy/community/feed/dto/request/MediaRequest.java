package com.ssafy.community.feed.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class MediaRequest {
    @Schema(description = "순서", example = "1")
    private Integer sequence;

    @Schema(description = "미디어 타입 (예: 이미지, 비디오)", example = "image")
    private String mediaType;

    @Schema(description = "미디어 파일")
    private MultipartFile file;



}
