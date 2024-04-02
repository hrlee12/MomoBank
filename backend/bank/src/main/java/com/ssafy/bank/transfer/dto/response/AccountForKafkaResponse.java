package com.ssafy.bank.transfer.dto.response;

import com.ssafy.bank.account.domain.AccountProduct.AccountType;
import com.ssafy.bank.account.dto.response.MemberForKafkaResponse;
import com.ssafy.bank.member.domain.Member;

public record AccountForKafkaResponse (
    int accountId,
    String accountNumber,
    String accountProductName,
    AccountType accountType,
    String bankName,
    String interestRate,
    String balance,
    MemberForKafkaResponse member

) {

}
