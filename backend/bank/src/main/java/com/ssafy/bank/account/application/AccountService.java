package com.ssafy.bank.account.application;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.AccountProduct;
import com.ssafy.bank.account.domain.repository.AccountProductRepository;
import com.ssafy.bank.account.domain.repository.AccountRepository;
import com.ssafy.bank.account.dto.response.AccountResponse;
import com.ssafy.bank.common.ErrorCode;
import com.ssafy.bank.common.exception.CustomException;
import com.ssafy.bank.member.domain.Member;
import com.ssafy.bank.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountProductRepository accountProductRepository;
    private final MemberRepository memberRepository;

    public AccountResponse createAccount(int memberId, int accountProductId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));
        if (member.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_MEMBER);
        }

        AccountProduct accountProduct = accountProductRepository.findById(accountProductId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT_PRODUCT));
        if (accountProduct.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_ACCOUNT_PRODUCT);
        }

        Account account = Account.builder()
            .accountProduct(accountProduct)
            .accountNumber("505-01"
                + String.format("%02d",accountProduct.getType())
                + String.format("%10d",accountProduct.getAccountProductId()))
            .member(member)
            .build();

        return AccountResponse.from(accountRepository.save(account), accountProduct);
    }

    public void deleteAccount(int memberId, int accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT));
        if (account.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_ACCOUNT);
        }
        account.softDelete();
    }
}
