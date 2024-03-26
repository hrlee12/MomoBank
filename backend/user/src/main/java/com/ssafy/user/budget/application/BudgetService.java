package com.ssafy.user.budget.application;

import com.ssafy.user.budget.domain.Budget;
import com.ssafy.user.budget.domain.repository.BudgetRepository;
import com.ssafy.user.budget.dto.request.UpdateBudgetRequest;
import com.ssafy.user.budget.dto.response.GetBudgetListResponse;
import com.ssafy.user.budget.dto.response.GetBudgetResponse;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupInfo.domain.repository.GroupInfoRepository;
import com.ssafy.user.groupMember.domain.repository.GroupMemberRepository;
import com.ssafy.user.member.entity.Member;
import com.ssafy.user.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetService {

    private final GroupInfoRepository groupInfoRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final BudgetRepository budgetRepository;

    public GetBudgetListResponse getBudgetsList(int memberId, int groupInfoId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        // 리스트 생성

        return new GetBudgetListResponse();
    }

    public GetBudgetResponse createNewBudget(int memberId, int groupInfoId, int monthlyDueDate,
        String name, long finalFee, Date finalDueDate) {
        Member member = memberCheck(memberId); // 모임장 권한 확인 필요
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        Budget budget = Budget.builder()
            .monthlyDueDate(monthlyDueDate)
            .name(name)
            .dueDate(finalDueDate)
            .finalMoney(finalFee)
            .groupInfo(groupInfo)
            .build();
        budgetRepository.save(budget);
        return GetBudgetResponse.from(budget);
    }

    public GetBudgetResponse updateBudget(int groupInfoId, int budgetId,
        UpdateBudgetRequest request) {
        Member member = memberCheck(request.memberId());
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        Budget budget = budgetCheck(budgetId);

        budget.updateBudget(request);
        budgetRepository.save(budget);

        return GetBudgetResponse.from(budget);
    }

    public GetBudgetResponse deleteBudget(int memberId, int groupInfoId, int budgetId) {
        Member member = memberCheck(memberId);
        GroupInfo groupInfo = groupInfoCheck(groupInfoId);
        Budget budget = budgetCheck(budgetId);

        budget.softDelete();
        budgetRepository.save(budget);
        return GetBudgetResponse.from(budget);
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

    private Budget budgetCheck(int budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_BUDGET));
        if (budget.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_BUDGET);
        }
        return budget;
    }
}
