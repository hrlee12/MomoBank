package com.ssafy.user.groupInfo.application;

import com.ssafy.user.bank.application.BankCallService;
import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.bank.domain.repository.AccountRepository;
import com.ssafy.user.bank.dto.request.TransferRequest;
import com.ssafy.user.budget.domain.Budget;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.repository.GroupInfoRepository;
import com.ssafy.user.groupInfo.dto.request.CreateNewGroupRequest;
import com.ssafy.user.groupInfo.dto.request.UpdateGroupDescriptionRequest;
import com.ssafy.user.groupInfo.dto.request.UpdateGroupNameRequest;

import com.ssafy.user.groupInfo.dto.response.*;
import com.ssafy.user.groupMember.application.GroupMemberService;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.groupMember.domain.GroupMember.memberType;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepository;
import com.ssafy.user.groupMember.dto.response.GroupMemberDTO;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupInfoService {

    private final GroupInfoRepository groupInfoRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final AccountRepository accountRepository;

    private final GroupMemberService groupMemberService;
    private final BankCallService bankCallService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // 참여중인 모든 모임 조회
    public GetMyGroupListResponse getMyGroups(int memberId) {
        Member member = memberCheck(memberId);
        List<GetMyGruopResponse> list = groupInfoRepository.findGroupInfoResponseByMember(member);
        return new GetMyGroupListResponse(list);
    }

    // 선택된 모임 상세 조회
    public GetGroupDetailsResponse getGroupDetails(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        GroupMember groupMember = groupMemberRepository.findGroupMemberByMemberAndGroupInfo(member, groupInfo);
        return GetGroupDetailsResponse.from(groupInfo, groupMember);
    }

    // 각 모임원이 달마다 납입한 금액 조회
    public List<GetFeesPerYearResponse> getFeesPerMonth(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId); // 선택된 모임원
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        Account account = groupInfo.getAccount();

        return groupInfoRepository.GetFeesPerYear(member);
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


        groupInfoRepository.save(groupInfo);

//        CreateGroupKafkaResponse groupResponse = new CreateGroupKafkaResponse(
//            groupInfo.getGroupInfoId(),
//            groupInfo.getMember().getMemberId(),
//            groupInfo.getDescription(),
//            groupInfo.getGroupName()
//        );

//        log.info("CreateGroupKafkaResponse : {}", groupResponse);

//        kafkaTemplate.send("createGroup", groupResponse);


        GroupMember groupMember = GroupMember.builder()
            .name(member.getName())
            .role(memberType.모임장)
            .groupInfo(groupInfo)
            .member(member)
            .account(myAccount)
            .build();

//<<<<<<< Updated upstream
//        groupInfoRepository.save(groupInfo);
//        groupMemberRepository.save(groupMember);
//
//=======


        groupMemberRepository.save(groupMember);


//        CreateGroupMemberKafkaResponse groupMemberRessponse = new CreateGroupMemberKafkaResponse(
//            groupMember.getGroupMemberId(),
//            groupMember.getGroupInfo().getGroupInfoId(),
//            groupMember.getMember().getMemberId(),
//            String.valueOf(groupMember.getTotalFee()),
//            groupMember.getName(),
//            groupMember.getRole().toString()
//        );

//        kafkaTemplate.send("createGroupMemberAsGroupCreated", groupMemberRessponse);
        CreateGroupAndGroupMemberResponse kafkaDto = CreateGroupAndGroupMemberResponse.builder()
                .groupMemberId(groupMember.getGroupMemberId())
                .createdBy(groupInfo.getMember().getMemberId())
                .groupInfoId(groupInfo.getGroupInfoId())
                .totalFee(String.valueOf(groupMember.getTotalFee()))
                .name(groupMember.getName())
                .memberId(groupMember.getMember().getMemberId())
                .groupName(groupInfo.getGroupName())
                .description(groupInfo.getDescription())
                .role(groupMember.getRole().toString())
                .build();


        kafkaTemplate.send("createGroupAndGroupMember", kafkaDto);

//        log.info("CreateGroupMemberKafkaResponse : {}", groupMemberRessponse);




//>>>>>>> Stashed changes
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
        Member member = memberCheck(memberId); // 모임장 권환
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        Account account = groupInfo.getAccount();
        List<GroupMemberDTO> list = groupMemberService.getAllGroupMembers(groupInfoId);


        long amount = account.getBalance()/list.size();

        System.out.println("멤버 수 : " + list.size());
        System.out.println("잔액 : " + account.getBalance());
        System.out.println("나눠주는 금액 : " + amount);

        for(GroupMemberDTO groupMember : list){
            Member toMember = memberCheck(groupMember.getId());
            Account personalAccount = groupMemberRepository.findAccountFromGroupMemberByMember(toMember, groupInfo);
            TransferRequest request = new TransferRequest(
                groupInfo.getAccount().getAccountId(),
                personalAccount.getAccountId(),
                amount
            );
            bankCallService.transfer(request);
        }

        return new SplitBalanceResponse(amount, list.size());
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
