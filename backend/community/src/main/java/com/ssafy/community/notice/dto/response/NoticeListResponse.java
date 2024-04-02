package com.ssafy.community.notice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "공지사항 리스트 응답 데이터")
@Getter
@Builder
public class NoticeListResponse {

    @Schema(description = "공지사항 제목", example = "새로운 기능 업데이트 안내")
    private int notedId;

    private String title;

    private String content;

    private String memberName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



}