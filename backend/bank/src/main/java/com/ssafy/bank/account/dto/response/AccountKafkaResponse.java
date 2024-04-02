package com.ssafy.bank.account.dto.response;

import com.ssafy.bank.account.domain.AccountProduct.AccountType;
import com.ssafy.bank.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountKafkaResponse {
    private int accountId;
    private String accountNumber;
    private AccountType accountType;
    private String bankName;
    private String interestRate;
    private String balance;
    private MemberForKafkaResponse member;
}
