package com.ssafy.bank.account.dto.response;

import com.ssafy.bank.account.domain.AccountProduct.AccountType;
import com.ssafy.bank.member.domain.Member;

public record AccountKafkaResponse(
    int accountId,
    String accountNumber,
    AccountType accountType,
    String bankName,
    float interestRate,
    long balance,
    Member member
) {

}
