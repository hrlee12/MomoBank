package com.ssafy.bank.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("accounts")
@Tag(name = "계좌", description = "계좌 생성 및 조회")
public class AccountController {

    @Operation(summary = "당행 계좌 상품 목록", description = "모모뱅크의 계좌 상품 목록을 조회합니다.")
    @GetMapping("/account-products")
    public ResponseEntity<?> getAccountProduct() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "신분증 진위 여부 확인", description = "신분증 진위 여부를 확인합니다.")
    @PostMapping("/lisence-check")
    public ResponseEntity<?> lisenceCheck() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "당행 계좌 생성", description = "모모뱅크의 계좌를 생성합니다.")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(
        @Parameter(description = "계좌 생성을 위한 상품 id와 사용자 id", required = true) @RequestBody int accountProductId) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "본인 계좌 조회", description = "사용자의 계좌를 조회합니다.")
    @GetMapping("/my-accounts")
    public ResponseEntity<?> getMyAccount() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "계좌 상세 조회", description = "특정 계좌 상세를 조회합니다.")
    @GetMapping("/account-detail")
    public ResponseEntity<?> getAccountDetail() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "본인 계좌 거래 내역 조회", description = "사용자의 계좌 거래 내역을 조회합니다.")
    @GetMapping("/get-transaction")
    public ResponseEntity<?> getTransaction() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "계좌 삭제", description = "사용자의 계좌를 삭제합니다.")
    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount() {
        return ResponseEntity.ok().build();
    }
}
