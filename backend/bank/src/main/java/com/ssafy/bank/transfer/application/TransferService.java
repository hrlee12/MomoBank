package com.ssafy.bank.transfer.application;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.account.domain.repository.AccountRepository;
import com.ssafy.bank.common.ErrorCode;
import com.ssafy.bank.common.exception.CustomException;
import com.ssafy.bank.transfer.domain.Transfer;
import com.ssafy.bank.transfer.domain.repository.TransferRepository;
import com.ssafy.bank.transfer.dto.request.TransferRequest;
import com.ssafy.bank.transfer.dto.response.TransferResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    public TransferResponse transfer(TransferRequest request) {

        Account fromAccount = accountCheck(request.fromAccountId());
        Account toAccount = accountCheck(request.toAccountId());

        if(fromAccount.getBalance() < request.amount()){
            throw new CustomException(ErrorCode.INSUFFICIENT_FUNDS);
        }

        fromAccount.updateBalance(fromAccount.getBalance()-request.amount());
        toAccount.updateBalance(toAccount.getBalance()+request.amount());

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

        return new TransferResponse(
            fromAccount.getAccountNumber(),
            request.fromAccountId(),
            toAccount.getMember().getName(),
            request.amount(),
            transfer.getDescription(),
            transfer.getFromBalance()
        );
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
