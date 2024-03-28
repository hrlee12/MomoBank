package com.ssafy.community.feed.presentation;
import com.ssafy.community.feed.application.FeedService;
import com.ssafy.community.feed.dto.*;
import com.ssafy.community.feed.dto.request.FeedCreateRequest;
import com.ssafy.community.feed.dto.request.FeedUpdateRequest;
import com.ssafy.community.feed.dto.response.FeedListResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    @Operation(summary = "피드 목록 조회",
            description = "페이지네이션된 피드 목록을 조회합니다. 페이지 번호, 페이지 크기, 정렬 방식을 매개변수로 사용할 수 있습니다.",
            responses = {
            @ApiResponse(responseCode = "200", description = "피드 목록 조회 성공",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Page<FeedListResponse>> getFeeds(@ParameterObject Pageable pageable, int memberId) {
        Page<FeedListResponse> feeds = feedService.getFeeds(pageable, memberId);
        return ResponseEntity.ok(feeds);
    }

//    @GetMapping("/{feedId}")
//    @Operation(summary = "피드 상세 정보 조회", responses = {
//            @ApiResponse(responseCode = "200", description = "피드 상세 조회 성공",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = FeedDetailResponseDto.class)))
//    })
//    public ResponseEntity<FeedDetailResponseDto> getFeedDetail(@PathVariable Long feedId) {
//        FeedDetailResponseDto feedDetail = new FeedDetailResponseDto();
//        // 데이터베이스 조회 로직을 통해 피드 상세 정보 가져오기 (생략)
//        return ResponseEntity.ok(feedDetail);
//    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "피드 등록", responses = {
            @ApiResponse(responseCode = "201", description = "피드 등록 성공")
    })
    public ResponseEntity<String> createFeed(@RequestPart("freeCreateRequest") FeedCreateRequest feedCreateRequest,
                                             @RequestPart(required = false) List<MultipartFile> files) {
//        feedCreateRequest.setFiles(files);
        feedService.createFeed(feedCreateRequest, files);
        return ResponseEntity.ok("피드가 성공적으로 등록되었습니다.");
    }

    @PutMapping("/{feedId}")
    @Operation(summary = "피드 수정", responses = {
            @ApiResponse(responseCode = "200", description = "피드 수정 성공")
    })
    public ResponseEntity<String> updateFeed(@PathVariable Integer feedId, @RequestBody FeedUpdateRequest feedRequestDto) {
        feedService.updateFeed(feedId, feedRequestDto);
        return ResponseEntity.ok("피드가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/{feedId}")
    @Operation(summary = "피드 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "피드 삭제 성공")
    })
    public ResponseEntity<String> deleteFeed(@PathVariable Integer feedId) {
        feedService.deleteFeed(feedId);
        return ResponseEntity.ok("피드가 성공적으로 삭제되었습니다.");
    }

    @PostMapping("/{feedId}/likes")
    @Operation(summary = "피드에 좋아요 추가", responses = {
            @ApiResponse(responseCode = "200", description = "좋아요 추가 성공", content = @Content(schema = @Schema(implementation = LikeDto.class)))
    })
    public ResponseEntity<String> addLike(@PathVariable Integer feedId, Integer memberId) {
        feedService.addLike(feedId, memberId);
        return ResponseEntity.ok("좋아요를 추가했습니다.");
    }

    @DeleteMapping("/{feedId}/likes")
    @Operation(summary = "피드에 추가된 좋아요 취소", responses = {
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공")
    })
    public ResponseEntity<String> removeLike(@PathVariable Integer feedId, Integer memberId) {
        feedService.removeLike(feedId, memberId);
        return ResponseEntity.ok("좋아요를 취소했습니다.");
    }

    @PostMapping("/{feedId}/comments")
    @Operation(summary = "피드에 댓글 작성", responses = {
            @ApiResponse(responseCode = "201", description = "댓글 작성 성공", content = @Content(schema = @Schema(implementation = CommentDto.class)))
    })
    public ResponseEntity<CommentDto> addComment(@PathVariable Integer feedId, @RequestBody CommentDto commentDto) {
        CommentDto createdComment = feedService.addComment(feedId, commentDto);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/{feedId}/comments/{commentId}")
    @Operation(summary = "댓글 수정", responses = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = @Content(schema = @Schema(implementation = CommentDto.class)))
    })
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer feedId, @PathVariable Integer commentId, @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = feedService.updateComment(commentId, commentDto);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{feedId}/comments/{commentId}")
    @Operation(summary = "댓글 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공")
    })
    public ResponseEntity<String> deleteComment(@PathVariable Integer feedId, @PathVariable Integer commentId) {
        feedService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }



}
