package com.ssafy.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("groups")
@Tag(name = "모임", description = "모임관련 데이터 생성, 조회, 수정, 삭제 및 상태 관리")
public class groupController {

    @Operation(summary = "전체 모임 조회", description = "참여중인 모든 모임 조회")
    @GetMapping("/my-groups")
    public ResponseEntity<?> getMyGroups() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모임 상세 조회", description = "선택된 모임의 상세 조회")
    @GetMapping("/group-details")
    public ResponseEntity<?> getGroupDetails() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모임원별 납입액", description = "각 모임원이 달마나 납입한 금액 조회")
    @GetMapping("/fees-per-month")
    public ResponseEntity<?> getFeesPerMonth() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모임 생성", description = "새 모임 생성")
    @PostMapping("/new-group")
    public ResponseEntity<?> createNewGroup() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모임 수정", description = "모임의 상세 정보 수정")
    @PostMapping("/group-details")
    public ResponseEntity<?> updateGroupDetails() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모임 회비 분배", description = "모임의 회비를 모임 구성원에게 분배")
    @PostMapping("/split-balance")
    public ResponseEntity<?> splitBalance() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모임 삭제", description = "모임 삭제")
    @DeleteMapping("")
    public ResponseEntity<?> deleteGroup() {
        return ResponseEntity.ok().build();
    }
}
