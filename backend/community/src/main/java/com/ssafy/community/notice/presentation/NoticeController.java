package com.ssafy.community.notice.presentation;

import com.ssafy.community.notice.application.NoticeService;
import com.ssafy.community.notice.dto.request.NoticeCreationRequest;
import com.ssafy.community.notice.dto.request.NoticeModificationRequest;
import com.ssafy.community.notice.dto.response.NoticeDetailResponse;
import com.ssafy.community.notice.dto.response.NoticeListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping("/list/{groupInfoId}")
    @Operation(summary = "공지사항 리스트 조회", description = "사용자가 전체 공지사항을 최신 순으로 확인할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 리스트 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoticeListResponse.class))})
    })
    public ResponseEntity<List<NoticeListResponse>> getNoticeList(@PathVariable int groupInfoId) {
        List<NoticeListResponse> noticeList = noticeService.getNoticeList(groupInfoId);
        return ResponseEntity.ok(noticeList);
    }

    @PostMapping("/create")
    @Operation(summary = "공지사항 작성", description = "권한이 있는 사용자가 공지사항을 작성할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "공지사항 작성 성공",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<String> createNotice(@RequestBody NoticeCreationRequest request) {
        noticeService.createNotice(request);
        return ResponseEntity.ok("공지사항이 성공적으로 작성되었습니다.");
    }

    @GetMapping("/{noticeId}")
    @Operation(summary = "공지사항 상세보기", description = "사용자가 공지사항 게시글의 상세 내용을 볼 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 상세 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoticeDetailResponse.class))})
    })
    public ResponseEntity<NoticeDetailResponse> getNoticeDetail(@PathVariable Integer noticeId) {
        NoticeDetailResponse noticeDetail = noticeService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(noticeDetail);
    }

    @PutMapping("/{noticeId}")
    @Operation(summary = "공지사항 수정", description = "권한이 있는 사용자가 공지사항을 수정할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 성공",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<String> modifyNotice(@PathVariable Integer noticeId, @RequestBody NoticeModificationRequest request) {
        noticeService.modifyNotice(request);
        return ResponseEntity.ok("공지사항이 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/{noticeId}")
    @Operation(summary = "공지사항 삭제", description = "권한이 있는 사용자가 공지사항을 삭제할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 삭제 성공",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<String> deleteNotice(@PathVariable Integer noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok("공지사항이 성공적으로 삭제되었습니다.");
    }

    // 공지사항 작성 후 전체 알림 기능은 백엔드 로직에 포함되어 FCM 등의 서비스를 통해 처리될 것으로 예상되므로,
    // 별도의 컨트롤러 메서드는 제공하지 않습니다. 해당 처리 로직은 'createNotice' 메서드 내에서 구현될 것입니다.
}
