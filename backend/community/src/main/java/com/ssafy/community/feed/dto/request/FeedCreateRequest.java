package com.ssafy.community.feed.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "피드 생성 요청 DTO")
@Getter
@Setter
public class FeedCreateRequest {
    @Schema(description = "피드 내용", example = "오늘 하늘은 정말 맑습니다.")
    private String content;

    @Schema(description = "그룹 멤버 ID", example = "1")
    private Integer groupMemberId;

    // ################## 첨부 파일 ##################

//    private List<MultipartFile> files;



}