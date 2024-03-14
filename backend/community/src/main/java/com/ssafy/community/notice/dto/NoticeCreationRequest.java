package com.ssafy.community.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공지사항 생성 요청")
@Getter
public class NoticeCreationRequest {

    @Schema(description = "공지사항 제목", example = "새로운 기능 업데이트 안내")
    private String title;

    @Schema(description = "공지사항 내용", example = "새로운 기능이 추가되었습니다. 자세한 내용을 확인해주세요.")
    private String content;

}

