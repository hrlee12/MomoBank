package com.ssafy.user.bank.application;

import com.ssafy.user.bank.dto.request.CreateAccountRequest;
import com.ssafy.user.bank.dto.request.CreateCardInfoRequest;
import com.ssafy.user.bank.dto.request.DeleteAccountRequest;
import com.ssafy.user.bank.dto.request.PasswordConfirmRequest;
import com.ssafy.user.bank.dto.request.TransferRequest;
import com.ssafy.user.common.exception.ApiException;
import com.ssafy.user.common.exception.ErrorResponse;
import com.ssafy.user.common.util.RestTemplateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Transactional
public class BankCallService {

    @Value("${bank.url}")
    private String bankUrl;

    private final RestTemplateUtil restTemplateUtil;

    // 당행 계좌 상품 목록 조회
    public ResponseEntity accountProductList() {
        ResponseEntity response;
        try {
            response = restTemplateUtil.send(bankUrl + "/accounts/account-products", HttpMethod.GET,
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
            response = restTemplateUtil.send(bankUrl + "/accounts/bank-list", HttpMethod.GET,
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
            response = restTemplateUtil.send(bankUrl + "/accounts/create-account", HttpMethod.POST,
                request);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
        return response;
    }

    // 계좌 삭제
    public ResponseEntity deleteAccount(DeleteAccountRequest request) {
        ResponseEntity response;
        try {
            response = restTemplateUtil.send(bankUrl + "/accounts/delete-account",
                HttpMethod.DELETE, request);
        } catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
        return response;
    }

    // 당행 카드 생성
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
    // 카드 삭제
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
        try{
            response = restTemplateUtil.send(bankUrl + "/transfers/transfer",
                HttpMethod.POST, request);
        }catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
        return response;
    }

     //비밀번호 확인
    public ResponseEntity passwordConfirm(PasswordConfirmRequest request){
        ResponseEntity response;
        try{
            response = restTemplateUtil.send(bankUrl + "/transfers/password-confirm",
                HttpMethod.POST, request);
        }catch (HttpClientErrorException e) {
            ErrorResponse errorResponse = e.getResponseBodyAs(ErrorResponse.class);
            throw new ApiException(errorResponse);
        }
        return response;
    }
}
