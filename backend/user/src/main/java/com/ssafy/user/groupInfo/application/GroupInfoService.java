package com.ssafy.user.groupInfo.application;

import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.bank.domain.repository.AccountRepository;
import com.ssafy.user.budget.domain.Budget;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.repository.GroupInfoRepository;
import com.ssafy.user.groupInfo.dto.request.CreateNewGroupRequest;
import com.ssafy.user.groupInfo.dto.request.UpdateGroupDescriptionRequest;
import com.ssafy.user.groupInfo.dto.request.UpdateGroupNameRequest;
import com.ssafy.user.groupInfo.dto.response.CreateNewGroupResponse;
import com.ssafy.user.groupInfo.dto.response.GetFeesListResponse;
import com.ssafy.user.groupInfo.dto.response.GetGroupDetailsResponse;
import com.ssafy.user.groupInfo.dto.response.GetMyGroupListResponse;
import com.ssafy.user.groupInfo.dto.response.GetMyGruopResponse;
import com.ssafy.user.groupInfo.dto.response.GroupResponse;
import com.ssafy.user.groupInfo.dto.response.SplitBalanceResponse;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.groupMember.domain.GroupMember.memberType;
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
    private final AccountRepository accountRepository;

    // 참여중인 모든 모임 조회
    public GetMyGroupListResponse getMyGroups(int memberId) {
        Member member = memberCheck(memberId);
        return new GetMyGroupListResponse(groupInfoRepository.findGroupInfoResponseByMember(memberId));
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
    public CreateNewGroupResponse createNewGroup(int memberId, CreateNewGroupRequest request) {
        Member member = memberCheck(memberId);
        Account account = accountCheck(request.accountId());
        Account myAccount = accountCheck(request.myAccountId());

        GroupInfo groupInfo = GroupInfo.builder()
            .groupName(request.groupName())
            .description(request.description())
            .member(member)
            .account(account)
            .build();

        GroupMember groupMember = GroupMember.builder()
            .name(member.getName())
            .role(memberType.모임장)
            .groupInfo(groupInfo)
            .member(member)
            .account(myAccount)
            .build();

        groupInfoRepository.save(groupInfo);
        groupMemberRepository.save(groupMember);
        return CreateNewGroupResponse.from(groupInfo);
    }

    // 모임 상세 정보
    public GroupResponse getGroupDetail(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        return groupInfoRepository.findGroupResponseByGroup(groupInfoId, memberId);
    }

    // 모임 이름 수정
    public GroupResponse updateGroupName(int memberId, int groupInfoId, UpdateGroupNameRequest request) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        groupInfo.updateGroupName(request.groupName());
        groupInfoRepository.save(groupInfo);
        return groupInfoRepository.findGroupResponseByGroup(groupInfoId, memberId);
    }

    // 모임 목적 수정
    public GroupResponse updateGroupDescription(int memberId, int groupInfoId, UpdateGroupDescriptionRequest request) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        groupInfo.updateDescription(request.description());
        groupInfoRepository.save(groupInfo);
        return groupInfoRepository.findGroupResponseByGroup(groupInfoId, memberId);
    }

    // 모임 회비 분배
    public SplitBalanceResponse splitBalance(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);

        // groupmember 조회
        // 송금

        return new SplitBalanceResponse();
    }

    // 모임 삭제
    public void deleteGroup(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);

        groupInfo.deleteAccount();
        for(Budget budget : groupInfo.getBudgets()) {
            if(!budget.isDeleted())budget.softDelete();
        }

        groupInfo.softDelete();
        groupInfoRepository.save(groupInfo);
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

    private Account accountCheck(int accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT));
        if (account.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_ACCOUNT);
        }
        return account;
    }
}
