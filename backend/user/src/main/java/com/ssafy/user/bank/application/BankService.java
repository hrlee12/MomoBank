package com.ssafy.user.bank.application;

import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.bank.domain.Account.AccountType;
import com.ssafy.user.bank.domain.Transfer;
import com.ssafy.user.bank.domain.repository.AccountRepository;
import com.ssafy.user.bank.domain.repository.TransferRepository;
import com.ssafy.user.bank.dto.response.AccountResponse;
import com.ssafy.user.bank.dto.response.GetMyAccountListResponse;
import com.ssafy.user.bank.dto.response.GetTransferListResponse;
import com.ssafy.user.bank.dto.response.SearchAccountResponse;
import com.ssafy.user.bank.dto.response.SearchFromAccountResponse;
import com.ssafy.user.bank.dto.response.SearchToAccountResponse;
import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.common.util.KafkaUtil;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BankService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;
    private final KafkaUtil kafkaUtil;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;

    @KafkaListener(topics = "createAccount", groupId = "user")
    public void createAccount(Object data) {
        Map<String, Object> accountInfo =  kafkaUtil.dataToMap(data);
        Member member = memberRepository.findById((int)accountInfo.get("member"))
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_MEMBER));

        Account account = Account.builder()
            .accountId((int)accountInfo.get("accountId"))
            .accountNumber((String)accountInfo.get("accountNumber"))
            .accountProductName((String)accountInfo.get("accountProductName"))
            .accountType(accountInfo.get("accountType").equals("입출금자유예금")? AccountType.입출금 : accountInfo.get("accountType").equals("정기예금")? AccountType.정기예금 : AccountType.적금)
            .bankName((String)accountInfo.get("bankName"))
            .interestRate(Float.parseFloat(accountInfo.get("interestRate").toString()))
            .balance(Long.parseLong(accountInfo.get("balance").toString()))
            .member(member)
            .build();

        accountRepository.save(account);
    }

    @KafkaListener(topics = "deleteAccount", groupId = "user")
    public void deleteAccount(Object data) {
        Map<String, Object> accountInfo =  kafkaUtil.dataToMap(data);

        Account account = accountCheck((int)accountInfo.get("accountId"));

        account.softDelete();
    }

    @KafkaListener(topics = "transfer", groupId = "user")
    public void transfer(Object data) {
        Map<String, Object> transferInfo =  kafkaUtil.dataToMap(data);

        Account fromAccount = accountRepository.findById((int)transferInfo.get("fromAccount"))
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT));
        Account toAccount = accountRepository.findById((int)transferInfo.get("toAccount"))
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_ACCOUNT));

        Transfer transfer = Transfer.builder()
            .transferId((int) transferInfo.get("transferId"))
            .amount(Long.parseLong(transferInfo.get("amount").toString()))
            .description((String) transferInfo.get("description"))
            .fromBalance(Long.parseLong(transferInfo.get("fromBalance").toString()))
            .toBalance(Long.parseLong(transferInfo.get("toBalance").toString()))
            .fromAccount(fromAccount)
            .toAccount(toAccount)
            .build();

        transferRepository.save(transfer);
    }


    // 본인 계좌 조회
    public GetMyAccountListResponse getMyAccount(int memberId) {
        return new GetMyAccountListResponse(accountRepository.findMyAccountByMember(memberId));
    }

    // 계좌 상세 조회
    public AccountResponse getAccountDetail(int memberId, int accountId) {
        return accountRepository.findAccountDetailByMember(memberId, accountId);
    }

    // 본인 거래 내역 조회
    public GetTransferListResponse getTransfer(int memberId, int accountId) {
        Account account = accountCheck(accountId);
        return accountRepository.findTransferByAccount(account);
    }

    // 계좌 조회
    public SearchAccountResponse searchAccount(int accountId, String bankName, String accountNumber) {
        SearchFromAccountResponse from = accountRepository.findFromAccountByBankAndAccount(accountId);
        SearchToAccountResponse to = accountRepository.findToAccountByBankAndAccount(bankName, accountNumber);
        return new SearchAccountResponse(from, to);
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
