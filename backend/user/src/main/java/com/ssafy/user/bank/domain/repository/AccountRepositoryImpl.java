package com.ssafy.user.bank.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.bank.domain.QAccount;
import com.ssafy.user.bank.dto.response.AccountResponse;
import com.ssafy.user.bank.dto.response.GetMyAccountResponse;
import com.ssafy.user.bank.dto.response.QAccountResponse;
import com.ssafy.user.bank.dto.response.QGetMyAccountResponse;
import com.ssafy.user.bank.dto.response.QSearchFromAccountResponse;
import com.ssafy.user.bank.dto.response.QSearchToAccountResponse;
import com.ssafy.user.bank.dto.response.SearchFromAccountResponse;
import com.ssafy.user.bank.dto.response.SearchToAccountResponse;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupMember.dto.response.AccountDTO;
import com.ssafy.user.groupMember.dto.response.QAccountDTO;
import com.ssafy.user.member.domain.QMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QAccount account = QAccount.account;
    private final QGroupInfo group = QGroupInfo.groupInfo;
    private final QMember member = QMember.member;

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
    public AccountResponse findAccountDetailByMember(int memberId, int accountId) {
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

//    public GetTransferListResponse getTransferList(int accountId){
//
//    }

    @Override
    public SearchToAccountResponse findToAccountByBankAndAccount(String bankName, String accountNumber){
        QAccount account = QAccount.account;
        QMember member = QMember.member;

        return queryFactory
            .select(new QSearchToAccountResponse(
                account.accountId,
                account.member.name
            ))
            .from(account)
            .leftJoin(account.member, member)
            .where(account.bankName.eq(bankName),
                account.accountNumber.eq(accountNumber))
            .fetchOne();
    }

    @Override
    public SearchFromAccountResponse findFromAccountByBankAndAccount(int accountId){
        QAccount account = QAccount.account;

        return queryFactory
            .select(new QSearchFromAccountResponse(
                account.member.name,
                account.balance
            ))
            .from(account)
            .where(account.accountId.eq(accountId))
            .fetchOne();
    }

    public List<AccountDTO> findAccountDTOByMemberId(String memberId) {

        return queryFactory.select(new QAccountDTO(account.accountId,
                account.accountNumber,
                account.accountProductName,
                account.bankName,
                account.balance))
            .from(account)
            .where(account.isDeleted.eq(false).and(account.member.id.eq(memberId)))
            .fetch();
    }


    public Account findAccountByIdAndMemberId(int accountId, String memberId) {
        return queryFactory.select(account)
            .from(account)
            .join(account.groupInfo, group)
            .fetchJoin()
            .join(account.member, member)
            .where(account.isDeleted.eq(false)
                .and(account.accountId.eq(accountId).and(account.member.id.eq(memberId))))
            .fetchOne();
    }

}
