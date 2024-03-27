package com.ssafy.user.budget.presentation;

import com.ssafy.user.budget.application.BudgetService;
import com.ssafy.user.budget.dto.request.BudgetRequest;
import com.ssafy.user.budget.dto.request.CreateNewBudgetRequest;
import com.ssafy.user.budget.dto.request.GetBudgetListRequest;
import com.ssafy.user.budget.dto.request.UpdateBudgetRequest;
import com.ssafy.user.budget.dto.response.GetBudgetListResponse;
import com.ssafy.user.budget.dto.response.GetBudgetResponse;
import com.ssafy.user.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("groups/{id}/budgets")
@Tag(name = "예산", description = "예산 생성, 수정, 삭제, 조회")
public class BudgetController {

    private final BudgetService budgetService;
    @Operation(summary = "현재 예산 목록 조회", description = "모임 내 예산 목록 조회")
    @GetMapping("/budgets-list")
    public ResponseEntity<?> getBudgetsList(@PathVariable int id,
        @RequestBody GetBudgetListRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "현재 예산 목록 조회 성공",
            budgetService.getBudgetsList(request.memberId(), id));
    }

    @Operation(summary = "예산 생성", description = "새 예산 생성")
    @PostMapping("/new-budget")
    public ResponseEntity<?> createNewBudget(@PathVariable int id,
        @RequestBody CreateNewBudgetRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "예산 생성 성공",
            budgetService.createNewBudget(request.memberId(), id, request.monthlyDueDate(),
                request.name(), request.finalFee(), request.finalDueDate()));
    }

    @Operation(summary = "예산 수정", description = "기존 예산 상세 수정")
    @PostMapping("/{budget_id}")
    public ResponseEntity<?> updateBudget(@PathVariable int id, @PathVariable int budget_id,
        @RequestBody UpdateBudgetRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "예산 수정 성공",
            budgetService.updateBudget(id, budget_id, request));
    }

    @Operation(summary = "예산 삭제", description = "기존 예산 삭제")
    @DeleteMapping("/{budget_id}")
    public ResponseEntity<?> deleteBudget(@PathVariable int id, @PathVariable int budget_id,
        @RequestBody BudgetRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "예산 삭제 성공",
            budgetService.deleteBudget(request.memberId(), id, budget_id));
    }
}
