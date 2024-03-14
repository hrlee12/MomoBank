package com.ssafy.user.budget.presentation;

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
@RequestMapping("budgets")
@Tag(name = "예산", description = "예산 생성, 수정, 삭제, 조회")
public class budgetController {

    @Operation(summary = "현재 예산 목록 조회", description = "모임 내 예산 목록 조회")
    @GetMapping("/budgets-list")
    public ResponseEntity<?> getBudgetsList() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "예산 생성", description = "새 예산 생성")
    @PostMapping("/new-budget")
    public ResponseEntity<?> createNewBudget() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "예산 수정", description = "기존 예산 상세 수정")
    @PostMapping("/budget")
    public ResponseEntity<?> updateBudget() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "예산 삭제", description = "기존 예산 삭제")
    @DeleteMapping("/budget")
    public ResponseEntity<?> deleteBudget() {
        return ResponseEntity.ok().build();
    }
}
