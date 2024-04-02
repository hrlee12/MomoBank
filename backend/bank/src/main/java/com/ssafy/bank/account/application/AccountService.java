package com.ssafy.bank.account.application;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.AccountProduct;
import com.ssafy.bank.account.domain.repository.AccountProductRepository;
import com.ssafy.bank.account.domain.repository.AccountRepository;
import com.ssafy.bank.account.dto.request.CreateAccountRequest;
import com.ssafy.bank.account.dto.request.DeleteAccountRequest;
import com.ssafy.bank.account.dto.response.AccountResponse;
import com.ssafy.bank.account.dto.response.BankResponse;
import com.ssafy.bank.account.dto.response.AccountKafkaResponse;
import com.ssafy.bank.account.dto.response.GetAllAccountProductResponse;
import com.ssafy.bank.account.dto.response.MemberForKafkaResponse;
import com.ssafy.bank.common.ErrorCode;
import com.ssafy.bank.common.exception.CustomException;
import com.ssafy.bank.member.domain.Member;
import com.ssafy.bank.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.security.SecureRandom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountProductRepository accountProductRepository;
    private final MemberRepository memberRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AccountResponse createAccount(CreateAccountRequest request) {
        log.info("CreateAccountRequest : {}", request);
        Member member = memberCheck(request.memberId());

        AccountProduct accountProduct = accountProductRepository.findById(
                request.accountProductId())
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT_PRODUCT));
        if (accountProduct.isDeleted()) {
            throw new CustomException(ErrorCode.DELETED_ACCOUNT_PRODUCT);
        }

        SecureRandom secureRandom = new SecureRandom();

        int secureRandomNumber = secureRandom.nextInt(1000);

        Account account = Account.builder()
            .accountProduct(accountProduct)
            .accountNumber("505-01-"
                + String.format("%03d",
                (accountProduct.getAccountProductId() * member.getMemberId()) % 1000)
                + String.format("%03d", secureRandomNumber))
            .accountPassword(request.accountPassword())
            .member(member)
            .build();

        accountRepository.save(account);

        MemberForKafkaResponse memberKafka = new MemberForKafkaResponse(member);

        AccountKafkaResponse response = new AccountKafkaResponse(
            account.getAccountId(),
            account.getAccountNumber(),
            accountProduct.getAccountType(),
            accountProduct.getBank().getBankName(),
            String.valueOf(accountProduct.getInterestRate()),
            String.valueOf(account.getBalance()),
            member.getMemberId()
        );

        log.info("AccountKafkaResponse : {}", response);

        kafkaTemplate.send("createAccount", response);

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

        MemberForKafkaResponse memberKafka = new MemberForKafkaResponse(member);

        AccountKafkaResponse response = new AccountKafkaResponse(
            account.getAccountId(),
            account.getAccountNumber(),
            accountProduct.getAccountType(),
            accountProduct.getBank().getBankName(),
            String.valueOf(accountProduct.getInterestRate()),
            String.valueOf(account.getBalance()),
            member.getMemberId()
        );

        kafkaTemplate.send("deleteAccount", response);

        return AccountResponse.from(account, accountProduct);
    }

    public GetAllAccountProductResponse getAccountProducts() {
        return new GetAllAccountProductResponse(accountRepository.findProductListByType());
    }

    public List<BankResponse> getBanks() {
        return accountRepository.getBank();
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
