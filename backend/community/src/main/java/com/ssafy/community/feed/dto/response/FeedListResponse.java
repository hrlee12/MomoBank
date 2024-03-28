package com.ssafy.community.feed.dto.response;

import com.ssafy.community.feed.dto.CommentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "피드 목록 조회 DTO")
@Getter
@Setter
@Builder
public class FeedListResponse {
    @Schema(description = "피드 ID", example = "1")
    private Integer feedId;

    @Schema(description = "내용", example = "피드 내용")
    private String content;

    @Schema(description = "내용(한 줄)", example = "피드 내용")
    private String contentOneLine;

    @Schema(description = "댓글 수", example = "5")
    private int commentsCount;

    @Schema(description = "좋아요 수", example = "10")
    private int likesCount;

    @Schema(description = "게시 날짜 및 시간")
    private LocalDateTime createdAt;

    @Schema(description = "업데이트 날짜 및 시간")
    private LocalDateTime updatedAt;

    private List<MediaResponse> mediaList; // 미디어 DTO 리스트

    @Schema(description = "좋아요 여부", example = "true")
    private boolean likedByUser; // 사용자가 좋아요를 눌렀는지 여부

    @Schema(description = "댓글 정보")
    private List<CommentDto> comments; // 댓글 DTO 리스트

    @Schema(description = "그룹 멤버 ID", example = "1")
    private Integer groupMemberId;

    @Schema(description = "그룹 멤버 이름", example = "홍길동")
    private String groupMemberName;



}
