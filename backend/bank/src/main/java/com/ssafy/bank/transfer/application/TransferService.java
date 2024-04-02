package com.ssafy.bank.transfer.application;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.repository.AccountRepository;
import com.ssafy.bank.account.dto.response.AccountKafkaResponse;
import com.ssafy.bank.account.dto.response.MemberForKafkaResponse;
import com.ssafy.bank.common.ErrorCode;
import com.ssafy.bank.common.exception.CustomException;
import com.ssafy.bank.transfer.domain.Transfer;
import com.ssafy.bank.transfer.domain.repository.TransferRepository;
import com.ssafy.bank.transfer.dto.request.PasswordConfirmRequest;
import com.ssafy.bank.transfer.dto.request.TransferRequest;
import com.ssafy.bank.transfer.dto.response.AccountForKafkaResponse;
import com.ssafy.bank.transfer.dto.response.PasswordConfirmResponse;
import com.ssafy.bank.transfer.dto.response.TransferKafkaResponse;
import com.ssafy.bank.transfer.dto.response.TransferResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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



        AccountForKafkaResponse fromAccountKafka = new AccountForKafkaResponse(
            fromAccount.getAccountId(),
            fromAccount.getAccountNumber(),
            fromAccount.getAccountProduct().getName(),
            fromAccount.getAccountProduct().getAccountType(),
            fromAccount.getAccountProduct().getBank().getBankName(),
            String.valueOf(fromAccount.getAccountProduct().getInterestRate()),
            String.valueOf(fromAccount.getBalance()),
            new MemberForKafkaResponse(fromAccount.getMember())
        );
        AccountForKafkaResponse toAccountKafka = new AccountForKafkaResponse(
            toAccount.getAccountId(),
            toAccount.getAccountNumber(),
            toAccount.getAccountProduct().getName(),
            toAccount.getAccountProduct().getAccountType(),
            toAccount.getAccountProduct().getBank().getBankName(),
            String.valueOf(toAccount.getAccountProduct().getInterestRate()),
            String.valueOf(toAccount.getBalance()),
            new MemberForKafkaResponse(toAccount.getMember())
        );

        TransferKafkaResponse response = new TransferKafkaResponse(
            transfer.getTransferId(),
            String.valueOf(transfer.getAmount()),
            transfer.getDescription(),
            String.valueOf(transfer.getFromBalance()),
            String.valueOf(transfer.getToBalance()),
            fromAccount.getAccountId(),
            toAccount.getAccountId()
        );

        kafkaTemplate.send("transfer", response);

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
