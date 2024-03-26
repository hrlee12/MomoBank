package com.ssafy.user.groupInfo.application;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.repository.GroupInfoRepository;
import com.ssafy.user.groupInfo.dto.response.CreateNewGroupResponse;
import com.ssafy.user.groupInfo.dto.response.GetFeesListResponse;
import com.ssafy.user.groupInfo.dto.response.GetGroupDetailsResponse;
import com.ssafy.user.groupInfo.dto.response.GetMyGroupListResponse;
import com.ssafy.user.groupInfo.dto.response.GetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.GroupResponse;
import com.ssafy.user.groupInfo.dto.response.SplitBalanceResponse;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepository;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupInfoService {

    private final GroupInfoRepository groupInfoRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    // 참여중인 모든 모임 조회
    public GetMyGroupListResponse getMyGroups(int memberId) {
        Member member = memberCheck(memberId);
        // 리스트 생성

        return new GetMyGroupListResponse();
    }

    public List<GroupMember> getMyGroup(Member member) {
        List<GroupMember> groupMembers = groupMemberRepository.findByMember(member);
        return groupMembers;
    }

    // 선택된 모임 상세 조회
    public GetGroupDetailsResponse getGroupDetails(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        return GetGroupDetailsResponse.from(groupInfo);
    }

    // 각 모임원이 달마다 납입한 금액 조회
    public GetFeesListResponse getFeesPerMonth(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);

        return new GetFeesListResponse();
    }

    // 새 모임 생성
    public CreateNewGroupResponse createNewGroup(int memberId) {
        Member member = memberCheck(memberId);
        // 그룹 생성

        // 모임장 참여자로 추가

        return new CreateNewGroupResponse();
    }

    // 모임 상세 정보
    public GroupResponse getGroupDetail(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);

        GroupInfo groupInfo = groupInfoCheck(groupInfoId);

        GroupResponse groupResponse = GroupResponse.builder()
            .name(groupInfo.getGroupName())
            .description(groupInfo.getDescription())
//            .availableBalance()
//            .totalFee()
//            .monthlyDueDate()
            .totalBalance(groupInfo.getAccount().getBalance())
//            .members()
            .build();
        return groupResponse;
    }

    // 모임 이름 수정
    public GroupResponse updateGroupName(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);

        GroupInfo groupInfo = groupInfoCheck(groupInfoId);

        GroupResponse groupResponse = GroupResponse.builder()
            .name(groupInfo.getGroupName())
            .description(groupInfo.getDescription())
//            .availableBalance()
//            .totalFee()
//            .monthlyDueDate()
            .totalBalance(groupInfo.getAccount().getBalance())
//            .members()
            .build();
        return groupResponse;
    }

    // 모임 목적 수정
    public GroupResponse updateGroupDescription(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);

        GroupInfo groupInfo = groupInfoCheck(groupInfoId);

        GroupResponse groupResponse = GroupResponse.builder()
            .name(groupInfo.getGroupName())
            .description(groupInfo.getDescription())
//            .availableBalance()
//            .totalFee()
//            .monthlyDueDate()
            .totalBalance(groupInfo.getAccount().getBalance())
//            .members()
            .build();
        return groupResponse;
    }

    // 모임 회비 분배
    public SplitBalanceResponse splitBalance(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);

        // 송금

        return new SplitBalanceResponse();
    }

    // 모임 삭제
    public GetMyGruopResponse deleteGroup(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);

        groupInfo.softDelete();
        groupInfoRepository.save(groupInfo);
        return new GetMyGruopResponse();
    }

    private Member memberCheck(int memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));
        if (member.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_MEMBER);
        }
        return member;
    }

    private GroupInfo groupInfoCheck(int groupInfoId) {
        GroupInfo groupInfo = groupInfoRepository.findById(groupInfoId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_GROUP_INFO));
        if (groupInfo.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_GROUP_INFO);
        }
        return groupInfo;
    }
}
