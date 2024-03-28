package com.ssafy.user.bank.application;

import com.ssafy.user.bank.domain.repository.AccountRepository;
import com.ssafy.user.bank.dto.response.AccountResponse;
import com.ssafy.user.bank.dto.response.GetMyAccountListResponse;
import com.ssafy.user.bank.dto.response.GetTransferListResponse;
import com.ssafy.user.bank.dto.response.SearchAccountResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BankService {

    private final AccountRepository accountRepository;

    // 본인 계좌 조회
    public GetMyAccountListResponse getMyAccount(int memberId) {
        return new GetMyAccountListResponse(accountRepository.findMyAccountByMember(memberId));
    }

    // 계좌 상세 조회
    public AccountResponse getAccountDetail(int memberId, int accountId) {
        return accountRepository.findAccountDetailByMember(memberId, accountId);
    }

    // 본인 거래 내역 조회
    public GetTransferListResponse getTransfer() {
        return new GetTransferListResponse();
    }

    // 계좌 조회
    public SearchAccountResponse searchAccount(String bankName, String accountNumber) {
        return accountRepository.findAccountByBankAndAccount(bankName, accountNumber);
    }

}
