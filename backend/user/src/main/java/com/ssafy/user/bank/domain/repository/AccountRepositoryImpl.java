package com.ssafy.user.bank.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.domain.QAccount;
import com.ssafy.user.bank.dto.response.AccountResponse;
import com.ssafy.user.bank.dto.response.GetMyAccountResponse;
import com.ssafy.user.bank.dto.response.QAccountResponse;
import com.ssafy.user.bank.dto.response.QGetMyAccountResponse;
import com.ssafy.user.bank.dto.response.QSearchAccountResponse;
import com.ssafy.user.bank.dto.response.SearchAccountResponse;
import com.ssafy.user.member.domain.QMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetMyAccountResponse> findMyAccountByMember(int memberId) {
        QAccount account = QAccount.account;

        return queryFactory
            .select(new QGetMyAccountResponse(
                account.accountId,
                account.accountProductName,
                account.accountType,
                account.accountNumber,
                account.balance
            ))
            .from(account)
            .where(account.member.memberId.eq(memberId))
            .fetch();
    }

    @Override
    public AccountResponse findAccountDetailByMember(int memberId, int accountId){
        QAccount account = QAccount.account;

        return queryFactory
            .select(new QAccountResponse(
                account.accountId,
                account.accountType,
                account.accountProductName,
                account.accountNumber,
                account.balance
            ))
            .from(account)
            .where(account.accountId.eq(accountId),
                account.member.memberId.eq(memberId))
            .fetchOne();
    }

    public SearchAccountResponse findAccountByBankAndAccount(String bankName, String accountNumber){
        QAccount account = QAccount.account;
        QMember member = QMember.member;

        return queryFactory
            .select(new QSearchAccountResponse(
                account.accountId,
                account.member.name
            ))
            .from(account)
            .leftJoin(account.member, member)
            .where(account.bankName.eq(bankName),
                account.accountNumber.eq(accountNumber))
            .fetchOne();
    }
}
