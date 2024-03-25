package com.ssafy.community.notice.controller;

import com.ssafy.community.notice.dto.NoticeCreationRequest;
import com.ssafy.community.notice.dto.NoticeDetailResponse;
import com.ssafy.community.notice.dto.NoticeListResponse;
import com.ssafy.community.notice.dto.NoticeModificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping("/list")
    @Operation(summary = "공지사항 리스트 조회", description = "사용자가 전체 공지사항을 최신 순으로 확인할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 리스트 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoticeListResponse.class))})
    })
    public ResponseEntity<List<NoticeListResponse>> getNoticeList() {
        // 구현 로직
        return ResponseEntity.ok().body(null); // 예시 구현
    }

    @PostMapping("/create")
    @Operation(summary = "공지사항 작성", description = "권한이 있는 사용자가 공지사항을 작성할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "공지사항 작성 성공",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<String> createNotice(@RequestBody NoticeCreationRequest request) {
        // 구현 로직
        return ResponseEntity.created(null).body("공지사항이 성공적으로 작성되었습니다.");
    }

    @GetMapping("/{id}")
    @Operation(summary = "공지사항 상세보기", description = "사용자가 공지사항 게시글의 상세 내용을 볼 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 상세 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NoticeDetailResponse.class))})
    })
    public ResponseEntity<NoticeDetailResponse> getNoticeDetail(@PathVariable Long id) {
        // 구현 로직
        return ResponseEntity.ok().body(null); // 예시 구현
    }

    @PutMapping("/{id}")
    @Operation(summary = "공지사항 수정", description = "권한이 있는 사용자가 공지사항을 수정할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 성공",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<String> modifyNotice(@PathVariable Long id, @RequestBody NoticeModificationRequest request) {
        // 구현 로직
        return ResponseEntity.ok("공지사항이 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "공지사항 삭제", description = "권한이 있는 사용자가 공지사항을 삭제할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 삭제 성공",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<String> deleteNotice(@PathVariable Long id) {
        // 구현 로직
        return ResponseEntity.ok("공지사항이 성공적으로 삭제되었습니다.");
    }

    // 공지사항 작성 후 전체 알림 기능은 백엔드 로직에 포함되어 FCM 등의 서비스를 통해 처리될 것으로 예상되므로,
    // 별도의 컨트롤러 메서드는 제공하지 않습니다. 해당 처리 로직은 'createNotice' 메서드 내에서 구현될 것입니다.
}
