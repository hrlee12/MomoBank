package com.ssafy.user.bank.presentation;

import com.ssafy.user.bank.application.BankService;
import com.ssafy.user.bank.dto.request.CreateAccountRequest;
import com.ssafy.user.bank.dto.request.GetAccountDetailRequest;
import com.ssafy.user.bank.dto.request.GetAccountTransferRequest;
import com.ssafy.user.bank.dto.request.GetMyAccountRequest;
import com.ssafy.user.bank.dto.request.SearchAccountRequest;
import com.ssafy.user.bank.dto.request.TransferRequest;
import com.ssafy.user.bank.dto.response.AccountResponse;
import com.ssafy.user.bank.dto.response.CardResponse;
import com.ssafy.user.bank.dto.response.GetAccountProductListResponse;
import com.ssafy.user.bank.dto.response.GetMyAccountListResponse;
import com.ssafy.user.bank.dto.response.GetTransferListResponse;
import com.ssafy.user.bank.dto.response.SearchAccountResponse;
import com.ssafy.user.bank.dto.response.TransferResponse;
import com.ssafy.user.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("bank")
@Tag(name = "은행", description = "은행의 계좌, 카드, 송금, 조회 기능")
public class BankController {
    private final BankService bankService;

    @Operation(summary = "당행 계좌 상품 목록", description = "모모뱅크의 계좌 상품 목록을 조회합니다.")
    @GetMapping("/account-products")
    public ResponseEntity<?> getAccountProduct(@RequestBody GetMyAccountRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "당행 계좌 상품 목록 조회 성공",
            bankService.getMyAccount(request.memberId()));
    }

    @Operation(summary = "본인 계좌 조회", description = "사용자의 계좌를 조회합니다.")
    @GetMapping("/my-accounts")
    public ResponseEntity<?> getMyAccount(@RequestBody GetMyAccountRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "사용자 계좌 조회 성공",
            bankService.getMyAccount(request.memberId()));
    }

    @Operation(summary = "계좌 상세 조회", description = "특정 계좌 상세를 조회합니다.")
    @GetMapping("/account-detail")
    public ResponseEntity<?> getAccountDetail(@RequestBody GetAccountDetailRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "계좌 상세 죄회 성공",
            bankService.getAccountDetail(request.memberId(), request.accountId()));
    }

    @Operation(summary = "본인 계좌 거래 내역 조회", description = "사용자의 계좌 거래 내역을 조회합니다.")
    @GetMapping("/get-transfer")
    public ResponseEntity<?> getTransafer(@RequestBody GetAccountTransferRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "계좌 거래 내역 죄회 성공",
            new GetTransferListResponse());
    }

    @Operation(summary = "당행 계좌 생성", description = "모모뱅크의 계좌를 생성합니다.")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 계좌를 생성했습니다.", new AccountResponse());
    }

    @Operation(summary = "계좌 삭제", description = "사용자의 계좌를 삭제합니다.")
    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount() {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 계좌를 삭제했습니다.", new AccountResponse());
    }

    @Operation(summary = "당행 카드 생성", description = "모모뱅크의 카드를 생성합니다.")
    @PostMapping("/create-card")
    public ResponseEntity<?> createCard() {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 카드를 생성했습니다.", new CardResponse());
    }

    @Operation(summary = "카드 삭제", description = "사용자의 카드를 삭제합니다.")
    @DeleteMapping("/delete-card")
    public ResponseEntity<?> deleteCard() {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 카드를 삭제했습니다.", new CardResponse());
    }

    @Operation(summary = "송금", description = "송금")
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "송금 성공", new TransferResponse());
    }

    @Operation(summary = "계좌 조회", description = "계좌 조회")
    @GetMapping("/account-search")
    public ResponseEntity<?> searchAccount(@RequestBody SearchAccountRequest request) {
        return CommonResponse.toResponseEntity(HttpStatus.OK, "계좌 조회 성공",
            bankService.searchAccount(request.bank(), request.accountNumber()));
    }

//    @Operation(summary = "신분증 진위 확인", description = "사용자의 신분증 진위를 확인합니다.")
//    @PostMapping("/varify")
//    public ResponseEntity<?> varifyIdCard() {
//        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 신분증의 진위를 확인했습니다.", null);
//
//    @Operation(summary = "신분증 OCR", description = "사용자의 신분증 정보를 확인합니다.")
//    @PostMapping("/varify/ocr")
//    public ResponseEntity<?> varifyIdCardOCR() {
//        return CommonResponse.toResponseEntity(HttpStatus.OK, "성공적으로 사용자의 신분증 정보를 확인했습니다.", null);
//    }
}
