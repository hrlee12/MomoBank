package com.ssafy.user.groupMember.presentation;


import com.ssafy.user.common.CommonResponse;
import com.ssafy.user.groupMember.application.GroupMemberService;
import com.ssafy.user.groupMember.dto.request.JoinGroupRequest;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;
import com.ssafy.user.groupMember.dto.response.GroupMemberListDTO;
import com.ssafy.user.groupMember.dto.response.InviteLinkResponse;
import com.ssafy.user.member.dto.response.VerificationTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/groups")
@Tag(name = "모임원 관리 api")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    @GetMapping("/{groupId}/members")
    @Operation(summary = "모임원 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임원 목록 불러오기 성공",
            content = {@Content(schema = @Schema(implementation = GroupMemberListDTO.class))}),
            @ApiResponse(responseCode = "400", description = "조회되는 그룹 멤버가 없습니다. 그룹 아이디를 확인해주세요.")})
    public ResponseEntity getGroupMembers(@PathVariable String groupId) {

        List<GroupMemberDTO> response = groupMemberService.getAllGroupMembers(Integer.parseInt(groupId));

        return CommonResponse.toResponseEntity(HttpStatus.OK, "모임원 목록 불러오기 성공", response);
    }


    @PostMapping("/{groupId}/invite")
    @Operation(summary = "모임 초대 링크 생성", responses = {
            @ApiResponse(responseCode = "200", description = "모임원 초대 링크 생성 성공",
                    content = @Content(schema = @Schema(implementation = InviteLinkResponse.class)))
    })
    public ResponseEntity getInviteLink(@PathVariable String groupId) throws Exception {

        String inviteLink = groupMemberService.getInviteLink(Integer.parseInt(groupId));
        return CommonResponse.toResponseEntity(HttpStatus.OK, "모임원 초대 링크 생성 성공", new InviteLinkResponse(inviteLink));
    }


    @PostMapping("/invite/{inviteCode}")
    @Operation(summary = "초대 링크 검증", responses = {
            @ApiResponse(responseCode = "200", description = "유효한 초대링크",
            content = @Content(schema = @Schema(implementation = VerificationTokenResponse.class))),
            @ApiResponse(responseCode = "410", description = "만료된 링크"),
            @ApiResponse(responseCode = "404", description = "삭제된 모임")
    })
    public ResponseEntity verifyInviteLink(@PathVariable String inviteCode) {
        // 구현 생략
        return ResponseEntity.ok().build();
    }


    @PostMapping("/invite/accounts")
    @Operation(summary = "연결할 계좌 선택하고 모임 가입", responses = {
            @ApiResponse(responseCode = "200", description = "모임 가입 완료"),
            @ApiResponse(responseCode = "410", description = "유효하지 않은 토큰")
    })
    public ResponseEntity joinGroup(@RequestBody JoinGroupRequest joinRequest) {
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{groupId}/members/{groupMemberId}")
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


    @DeleteMapping("/{groupId}/members")
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
