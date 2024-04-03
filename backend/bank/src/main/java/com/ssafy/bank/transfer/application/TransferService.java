package com.ssafy.bank.transfer.application;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.repository.AccountRepository;
import com.ssafy.bank.common.ErrorCode;
import com.ssafy.bank.common.exception.CustomException;
import com.ssafy.bank.transfer.domain.Transfer;
import com.ssafy.bank.transfer.domain.repository.TransferRepository;
import com.ssafy.bank.transfer.dto.request.PasswordConfirmRequest;
import com.ssafy.bank.transfer.dto.request.TransferRequest;
import com.ssafy.bank.transfer.dto.response.PasswordConfirmResponse;
import com.ssafy.bank.transfer.dto.response.TransferKafkaResponse;
import com.ssafy.bank.transfer.dto.response.TransferResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void sendDelayedMessage(TransferKafkaResponse response) {
        // CompletableFuture를 사용해 2초 후에 메시지 전송 작업을 비동기적으로 실행
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3); // 3초 동안 대기
                kafkaTemplate.send("transfer", response); // 메시지 전송
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // 에러 핸들링 (예: 로깅)
            }
        });
    }

    public TransferResponse transfer(TransferRequest request) {

        Account fromAccount = accountCheck(request.fromAccountId());
        Account toAccount = accountCheck(request.toAccountId());

        if (fromAccount.getBalance() < request.amount()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_FUNDS);
        }

        fromAccount.updateBalance(fromAccount.getBalance() - request.amount());
        toAccount.updateBalance(toAccount.getBalance() + request.amount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transfer transfer = Transfer.builder()
            .amount(request.amount())
            .description(fromAccount.getMember().getName())
            .fromBalance(fromAccount.getBalance())
            .toBalance(toAccount.getBalance())
            .fromAccount(fromAccount)
            .toAccount(toAccount)
            .build();

        transferRepository.save(transfer);

        TransferKafkaResponse response = new TransferKafkaResponse(
            transfer.getTransferId(),
            transfer.getAmount(),
            transfer.getDescription(),
            transfer.getFromBalance(),
            transfer.getToBalance(),
            transfer.getFromAccount(),
            transfer.getToAccount()
        );

        sendDelayedMessage(response);

        return new TransferResponse(
            fromAccount.getAccountNumber(),
            request.fromAccountId(),
            toAccount.getMember().getName(),
            request.amount(),
            transfer.getDescription(),
            transfer.getFromBalance());
    }

    public PasswordConfirmResponse passwordConfirm(PasswordConfirmRequest request) {
        Account account = accountCheck(request.accountId());

        return new PasswordConfirmResponse(account.getAccountPassword().equals(request.password()));
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
