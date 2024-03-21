package com.ssafy.community.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공지사항 수정 요청")
@Getter
public class NoticeModificationRequest {

    @Schema(description = "공지사항 제목", example = "업데이트 안내 수정")
    private String title;

    @Schema(description = "공지사항 내용", example = "업데이트 내용에 오류가 있어 수정합니다.")
    private String content;
}
