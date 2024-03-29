package com.ssafy.bank.account.application;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.AccountProduct;
import com.ssafy.bank.account.domain.repository.AccountProductRepository;
import com.ssafy.bank.account.domain.repository.AccountRepository;
import com.ssafy.bank.account.dto.request.CreateAccountRequest;
import com.ssafy.bank.account.dto.request.DeleteAccountRequest;
import com.ssafy.bank.account.dto.response.AccountResponse;
import com.ssafy.bank.account.dto.response.GetAllAccountProductResponse;
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

    public AccountResponse createAccount(CreateAccountRequest request) {
        Member member = memberCheck(request.memberId());

        AccountProduct accountProduct = accountProductRepository.findById(
                request.accountProductId())
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT_PRODUCT));
        if (accountProduct.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_ACCOUNT_PRODUCT);
        }

        Account account = Account.builder()
            .accountProduct(accountProduct)
            .accountNumber("505-01-"
                + String.format("%08d", accountProduct.getAccountProductId()))
            .accountPassword(request.accountPassword())
            .member(member)
            .build();
        accountRepository.save(account);

        return AccountResponse.from(account, accountProduct);
    }

    public AccountResponse deleteAccount(DeleteAccountRequest request) {
        Member member = memberCheck(request.memberId());

        Account account = accountRepository.findById(request.accountId())
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT));
        if (account.getBalance() != 0) {
            throw new CustomException(ErrorCode.YET_TO_BE_DELETED);
        }
        if (account.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_ACCOUNT);
        }

        if (!request.accountPassword().equals(account.getAccountPassword())) {
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        AccountProduct accountProduct = accountProductRepository.findById(
                account.getAccountProduct().getAccountProductId())
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT_PRODUCT));

        account.softDelete();
        return AccountResponse.from(account, accountProduct);
    }

    public GetAllAccountProductResponse getAccountProducts(){
        return new GetAllAccountProductResponse(accountRepository.findProductListByType());
    }

    private Member memberCheck(int memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));
        if (member.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_MEMBER);
        }
        return member;
    }

}
