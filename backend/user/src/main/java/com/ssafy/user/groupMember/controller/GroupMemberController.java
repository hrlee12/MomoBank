package com.ssafy.user.groupMember.controller;


import com.ssafy.user.groupMember.dto.request.JoinGroupRequest;
import com.ssafy.user.groupMember.dto.response.GroupMemberListDTO;
import com.ssafy.user.member.dto.response.VerifyCodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@Tag(name = "모임원 관리 api")
public class GroupMemberController {

    @GetMapping("/{groupId}/members")
    @Operation(summary = "모임원 목록 불러오기")
    @ApiResponse(responseCode = "200", description = "모임원 목록 불러오기 성공",
            content = {@Content(schema = @Schema(implementation = GroupMemberListDTO.class))})
    public ResponseEntity getGroupMembers(@PathVariable String groupId) {
        // 구현 생략
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/groups/{groupId}/invite")
    @Operation(summary = "모임 초대 링크 생성", responses = {
            @ApiResponse(responseCode = "200", description = "모임원 초대 링크 생성 성공",
                    content = @Content(schema = @Schema(implementation = VerifyCodeResponse.class)))
    })
    public ResponseEntity createInviteLink(@PathVariable String groupId) {

        return ResponseEntity.ok().build();
    }


    @PostMapping("/user/groups/invite/{inviteCode}")
    @Operation(summary = "초대 링크 검증", responses = {
            @ApiResponse(responseCode = "200", description = "유효한 초대링크",
            content = @Content(schema = @Schema(implementation = VerifyCodeResponse.class))),
            @ApiResponse(responseCode = "410", description = "만료된 링크"),
            @ApiResponse(responseCode = "404", description = "삭제된 모임")
    })
    public ResponseEntity verifyInviteLink(@PathVariable String inviteCode) {
        // 구현 생략
        return ResponseEntity.ok().build();
    }


    @PostMapping("/user/groups/invite/accounts")
    @Operation(summary = "연결할 계좌 선택하고 모임 가입", responses = {
            @ApiResponse(responseCode = "200", description = "모임 가입 완료"),
            @ApiResponse(responseCode = "410", description = "유효하지 않은 토큰")
    })
    public ResponseEntity joinGroup(@RequestBody JoinGroupRequest joinRequest) {
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/user/groups/{groupId}/members/{groupMemberId}")
    @Operation(summary = "모임원 추방")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임원 추방 완료"),
            @ApiResponse(responseCode = "404", description = "해당 모임원 없음"),
            @ApiResponse(responseCode = "400", description = "그룹장은 추방 불가")
    })
    public ResponseEntity removeGroupMember(@PathVariable String groupId, @PathVariable String groupMemberId) {
        // 로직 구현
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/user/groups/{groupId}/members")
    @Operation(summary = "모임 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임 탈퇴 완료"),
            @ApiResponse(responseCode = "404", description = "이미 삭제된 회원"),
            @ApiResponse(responseCode = "400", description = "그룹장은 탈퇴 불가")
    })
    public ResponseEntity leaveGroup(@PathVariable String groupId) {
        // 로직 구현
        return ResponseEntity.ok().build();
    }
}
