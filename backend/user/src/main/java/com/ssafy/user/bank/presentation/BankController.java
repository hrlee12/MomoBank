package com.ssafy.user.bank.presentation;

import com.ssafy.user.bank.application.BankCallService;
import com.ssafy.user.bank.application.BankService;
import com.ssafy.user.bank.dto.request.CreateAccountRequest;
import com.ssafy.user.bank.dto.request.DeleteAccountRequest;
import com.ssafy.user.bank.dto.request.GetAccountDetailRequest;
import com.ssafy.user.bank.dto.request.GetAccountTransferRequest;
import com.ssafy.user.bank.dto.request.GetMyAccountRequest;
import com.ssafy.user.bank.dto.request.PasswordConfirmRequest;
import com.ssafy.user.bank.dto.request.SearchAccountRequest;
import com.ssafy.user.bank.dto.request.TransferRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("bank")
@Tag(name = "은행", description = "은행의 계좌, 카드, 송금, 조회 기능")
public class BankController {

    private final BankService bankService;
    private final BankCallService bankCallService;

    @Operation(summary = "당행 계좌 상품 목록", description = "모모뱅크의 계좌 상품 목록을 조회합니다.")
    @GetMapping("/account-products")
    public ResponseEntity<?> getAccountProduct() {
        return bankCallService.accountProductList();
    }

    @Operation(summary = "은행사 목록", description = "은행사를 조회합니다.")
    @GetMapping("/bank-list")
    public ResponseEntity<?> getBankList() {
        return bankCallService.bankList();
    }

    @Operation(summary = "본인 계좌 조회", description = "사용자의 계좌를 조회합니다.")
    @GetMapping("/my-accounts/{id}")
    public ResponseEntity<?> getMyAccount(@PathVariable int id) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "사용자 계좌 조회 성공",
            bankService.getMyAccount(id));
    }

    @Operation(summary = "계좌 상세 조회", description = "특정 계좌 상세를 조회합니다.")
    @GetMapping("/account-detail")
    public ResponseEntity<?> getAccountDetail(
        @RequestParam int memberId,
        @RequestParam int accountId) {
        GetAccountDetailRequest request = new GetAccountDetailRequest(memberId, accountId);
        return CommonResponse.toResponseEntity(HttpStatus.OK, "계좌 상세 죄회 성공",
            bankService.getAccountDetail(request.memberId(), request.accountId()));
    }

    @Operation(summary = "본인 계좌 거래 내역 조회", description = "사용자의 계좌 거래 내역을 조회합니다.")
    @GetMapping("/get-transfer")
    public ResponseEntity<?> getTransafer(
        @RequestParam int memberId,
        @RequestParam int accountId) {
        GetAccountTransferRequest request = new GetAccountTransferRequest(memberId, accountId);
        return CommonResponse.toResponseEntity(HttpStatus.OK, "계좌 거래 내역 죄회 성공",
            bankService.getTransfer(request.memberId(), request.accountId()));
    }

    @Operation(summary = "당행 계좌 생성", description = "모모뱅크의 계좌를 생성합니다.")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        return bankCallService.createAccount(request);
    }

    @Operation(summary = "계좌 삭제", description = "사용자의 계좌를 삭제합니다.")
    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestBody DeleteAccountRequest request) {
        return bankCallService.deleteAccount(request);
    }

//    @Operation(summary = "당행 카드 생성", description = "모모뱅크의 카드를 생성합니다.")
//    @PostMapping("/create-card")
//    public ResponseEntity<?> createCard(@RequestBody CreateCardInfoRequest request) {
//        return bankCallService.createCard(request);
//    }
//
//    @Operation(summary = "카드 삭제", description = "사용자의 카드를 삭제합니다.")
//    @DeleteMapping("/delete-card")
//    public ResponseEntity<?> deleteCard(@RequestBody DeleteCardRequest request) {
//        return bankCallService.deleteCard(request);
//    }

    @Operation(summary = "송금", description = "송금")
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        return bankCallService.transfer(request);
    }

    @Operation(summary = "비밀번호 확인", description = "계좌 비밀번호 확인")
    @PostMapping("/password-confirm")
    public ResponseEntity<?> passwordConfirm(@RequestBody PasswordConfirmRequest request) {
        return bankCallService.passwordConfirm(request);
    }

    @Operation(summary = "계좌 조회", description = "계좌 조회")
    @PostMapping("/account-search")
    public ResponseEntity<?> searchAccount(
        @RequestBody SearchAccountRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "계좌 조회 성공",
            bankService.searchAccount(request.myAccountId(), request.bankName(),
                request.accountNumber()));
    }

}
