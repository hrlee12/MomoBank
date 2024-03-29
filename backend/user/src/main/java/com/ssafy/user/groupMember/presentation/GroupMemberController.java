package com.ssafy.user.groupMember.presentation;


import com.ssafy.user.common.CommonResponse;
import com.ssafy.user.groupMember.application.GroupMemberService;
import com.ssafy.user.groupMember.dto.request.GroupMemberIdDTO;
import com.ssafy.user.groupMember.dto.request.JoinGroupRequest;
import com.ssafy.user.groupMember.dto.request.MemberIdDTO;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;
import com.ssafy.user.groupMember.dto.response.GroupMemberListDTO;
import com.ssafy.user.groupMember.dto.response.InviteLinkResponse;
import com.ssafy.user.groupMember.dto.response.VerifyInviteCodeResponse;
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
import java.util.Map;

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
            @ApiResponse(responseCode = "400", description = "조회되는 그룹 멤버가 없습니다.")})
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
            @ApiResponse(responseCode = "200", description = "유효한 초대링크. 인증토큰 발급 완료",
            content = @Content(schema = @Schema(implementation = VerifyInviteCodeResponse.class))),
            @ApiResponse(responseCode = "400", description = "해당 초대링크가 존재하지 않습니다. 만료된 링크인지 확인해주세요."),
            @ApiResponse(responseCode = "404", description = "삭제된 모임"),
            @ApiResponse(responseCode = "409", description = "해당 모임에 이미 가입한 회원입니다.")
    })
    public ResponseEntity verifyInviteCode(@PathVariable String inviteCode, @RequestBody MemberIdDTO request) throws Exception {

        VerifyInviteCodeResponse response = groupMemberService.verifyInviteCode(inviteCode, request.getMemberId());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "유효한 초대링크. 인증토큰 발급 완료", response);
    }


    @PostMapping("/invite/accounts")
    @Operation(summary = "연결할 계좌 선택하고 모임 가입", responses = {
            @ApiResponse(responseCode = "200", description = "모임 가입 완료"),
            @ApiResponse(responseCode = "400", description = "인증정보 불일치 <br> 제공된 정보와 일치하는 회원 정보 없음 <br> 존재하지 않는 계좌입니다.<br> 주어진 아이디로 조회되는 그룹이 없습니다. 그룹 아이디를 확인해주세요.")

    })
    public ResponseEntity joinGroup(@RequestBody JoinGroupRequest request) throws Exception {

        groupMemberService.joinGroup(request.getAuthToken(), request.getGroupId(), request.getAccountId(), request.getMemberId());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "모임 가입 완료", null);
    }


    @DeleteMapping("/{groupId}/members/{groupMemberId}")
    @Operation(summary = "모임원 추방")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임원 추방 완료"),
            @ApiResponse(responseCode = "400", description = "조회되는 그룹 멤버가 없습니다.<br>모임장은 탈퇴할 수 없습니다.")
    })
    public ResponseEntity removeGroupMember(@PathVariable int groupId, @PathVariable int groupMemberId) {

        groupMemberService.removeGroupMember(groupId, groupMemberId);
        return CommonResponse.toResponseEntity(HttpStatus.OK, "모임원 추방 완료", null);
    }


    @DeleteMapping("/{groupId}/members")
    @Operation(summary = "모임 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모임 탈퇴 완료"),
            @ApiResponse(responseCode = "400", description = "조회되는 그룹 멤버가 없습니다.<br>모임장은 탈퇴할 수 없습니다.<br> 해당 회원의 그룹멤버 정보가 아닙니다. ")
    })
    public ResponseEntity leaveGroup(@PathVariable int groupId, @RequestBody GroupMemberIdDTO request) {
        // 로직 구현
        groupMemberService.leaveGroup(groupId, request.getGroupMemberId(), request.getMemberId());

        return CommonResponse.toResponseEntity(HttpStatus.OK, "모임 탈퇴 완료", null);
    }
}
