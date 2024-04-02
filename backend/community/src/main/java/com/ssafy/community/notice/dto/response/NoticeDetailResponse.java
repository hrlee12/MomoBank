package com.ssafy.community.notice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "공지사항 상세 정보 응답")
@Builder
public class NoticeDetailResponse {
    private Integer noticeId;

    @Schema(description = "공지사항 제목", example = "새로운 기능 업데이트 안내")
    private String title;

    @Schema(description = "공지사항 내용", example = "새로운 기능이 추가되었습니다. 자세한 내용을 확인해주세요.")
    private String content;

    @Schema(description = "게시 날짜", example = "2023-03-15")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Getters and Setters
}

