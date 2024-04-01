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
import jakarta.transaction.Transactional;
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

    @KafkaListener(topics = "createAccount", groupId = "user")
    public void createAccount(Object data) {
        Map<String, Object> accountInfo =  kafkaUtil.dataToMap(data);

        Account account = Account.builder()
            .accountId((int)accountInfo.get("accountId"))
            .accountNumber((String)accountInfo.get("accountNumber"))
            .accountProductName((String)accountInfo.get("accountProductName"))
            .accountType((AccountType) accountInfo.get("accountType"))
            .bankName((String)accountInfo.get("bankName"))
            .interestRate((float)accountInfo.get("interestRate"))
            .balance((long)accountInfo.get("balance"))
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

        Transfer transfer = Transfer.builder()
            .transferId((int) transferInfo.get("transferId"))
            .amount((long) transferInfo.get("amount"))
            .description((String) transferInfo.get("description"))
            .fromBalance((long) transferInfo.get("fromBalance"))
            .toBalance((long) transferInfo.get("toBalance"))
            .fromAccount((Account) transferInfo.get("fromAccount"))
            .toAccount((Account) transferInfo.get("toAccount"))
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
