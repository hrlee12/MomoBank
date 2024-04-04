package com.ssafy.user.bank.application;

import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.bank.domain.repository.AccountRepository;
import com.ssafy.user.bank.dto.request.CreateAccountRequest;
import com.ssafy.user.bank.dto.request.CreateCardInfoRequest;
import com.ssafy.user.bank.dto.request.DeleteAccountRequest;
import com.ssafy.user.bank.dto.request.DeleteCardRequest;
import com.ssafy.user.bank.dto.request.PasswordConfirmRequest;
import com.ssafy.user.bank.dto.request.TransferRequest;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.ApiException;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.exception.ErrorResponse;
import com.ssafy.user.common.util.RestTemplateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BankCallService {

    @Value("${bank.url}")
    private String bankUrl;

    private final RestTemplateUtil restTemplateUtil;
    private final AccountRepository accountRepository;

    // 당행 계좌 상품 목록 조회
    public ResponseEntity accountProductList() {
        ResponseEntity response;
        try {
            response = restTemplateUtil.send(bankUrl + "/api/bank/accounts/account-products", HttpMethod.GET,
                null);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
        return response;
    }

    // 은행사 조회
    public ResponseEntity bankList() {
        ResponseEntity response;
        try {
            response = restTemplateUtil.send(bankUrl + "/api/bank/accounts/bank-list", HttpMethod.GET,
                null);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
        return response;
    }
    // 당행 계좌 생성
    public ResponseEntity createAccount(CreateAccountRequest request) {
        ResponseEntity response;
        try {
            log.info("CreateAccountRequest: {}", request);
            log.info("send to {}", bankUrl + "/api/bank/accounts/create-account");
            response = restTemplateUtil.send(bankUrl + "/api/bank/accounts/create-account", HttpMethod.POST,
                request);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            log.info("createAccount errorResponse: {}", errorResponse);
            throw new ApiException(errorResponse);
        }
        return response;
    }

    // 계좌 삭제
    public ResponseEntity deleteAccount(DeleteAccountRequest request) {
        ResponseEntity response;
        try {
            response = restTemplateUtil.send(bankUrl + "/api/bank/accounts/delete-account",
                HttpMethod.DELETE, request);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
        return response;
    }

//    // 당행 카드 생성
//    public ResponseEntity createCard(CreateCardInfoRequest request) {
//        ResponseEntity response;
//        try {
//            response = restTemplateUtil.send(bankUrl + "card/create-card", HttpMethod.POST,
//                request);
//        } catch (HttpClientErrorException e) {
//            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
//            throw new ApiException(errorResponse);
//        }
//        return response;
//    }
//    // 카드 삭제
//    public ResponseEntity deleteCard(DeleteCardRequest request) {
//        ResponseEntity response;
//        try {
//            response = restTemplateUtil.send(bankUrl + "card/delete-card",
//                HttpMethod.DELETE, request);
//        } catch (HttpClientErrorException e) {
//            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
//            throw new ApiException(errorResponse);
//        }
//        return response;
//    }
    // 송금
    public ResponseEntity transfer(TransferRequest request){
        ResponseEntity response;

        Account fromAccount = accountCheck(request.fromAccountId());
        Account toAccount = accountCheck(request.toAccountId());

        if (fromAccount.getBalance() < request.amount()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_FUNDS);
        }

        fromAccount.updateBalance(fromAccount.getBalance() - request.amount());
        toAccount.updateBalance(toAccount.getBalance() + request.amount());

        try{
            response = restTemplateUtil.send(bankUrl + "/api/bank/transfers/transfer",
                HttpMethod.POST, request);
        }catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }finally {
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        }
        return response;
    }

     //비밀번호 확인
    public ResponseEntity passwordConfirm(PasswordConfirmRequest request){
        ResponseEntity response;
        try{
            response = restTemplateUtil.send(bankUrl + "/api/bank/transfers/password-confirm",
                HttpMethod.POST, request);
        }catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
        return response;
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
