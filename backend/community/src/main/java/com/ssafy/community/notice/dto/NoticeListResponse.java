package com.ssafy.community.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공지사항 리스트 응답 데이터")
@Getter
public class NoticeListResponse {

    @Schema(description = "공지사항 제목", example = "새로운 기능 업데이트 안내")
    private String title;

    @Schema(description = "게시 날짜", example = "2023-03-15")
    private String postedDate;

    @Schema(description = "게시자 이름", example = "모임장")
    private String posterName;

}