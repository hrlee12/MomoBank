package com.ssafy.bank.account.presentation;

import com.ssafy.bank.account.application.AccountService;
import com.ssafy.bank.account.dto.request.CreateAccountRequest;
import com.ssafy.bank.account.dto.request.DeleteAccountRequest;
import com.ssafy.bank.account.dto.response.AccountResponse;
import com.ssafy.bank.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("accounts")
@Tag(name = "계좌", description = "계좌 생성 및 조회")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "당행 계좌 생성", description = "모모뱅크의 계좌를 생성합니다.")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(
        @Parameter(description = "계좌 생성을 위한 상품 id와 사용자 id", required = true)
        @RequestBody CreateAccountRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 계좌를 생성했습니다.", accountResponse);
    }

    @Operation(summary = "계좌 삭제", description = "사용자의 계좌를 삭제합니다.")
    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@Parameter(description = "계좌 삭제를 위한 계좌 id", required = true)
    @RequestBody DeleteAccountRequest request) {
        AccountResponse accountResponse = accountService.deleteAccount(request);
        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 계좌를 삭제했습니다.", accountResponse);
    }
}
