package com.ssafy.user.bank.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.bank.domain.QAccount;
import com.ssafy.user.bank.domain.QTransfer;
import com.ssafy.user.bank.dto.response.AccountResponse;
import com.ssafy.user.bank.dto.response.GetMyAccountResponse;
import com.ssafy.user.bank.dto.response.GetTransferListPerDateResponse;
import com.ssafy.user.bank.dto.response.GetTransferListResponse;
import com.ssafy.user.bank.dto.response.GetTransferResponse;
import com.ssafy.user.bank.dto.response.QAccountResponse;
import com.ssafy.user.bank.dto.response.QGetMyAccountResponse;
import com.ssafy.user.bank.dto.response.QGetTransferResponse;
import com.ssafy.user.bank.dto.response.QSearchFromAccountResponse;
import com.ssafy.user.bank.dto.response.QSearchToAccountResponse;
import com.ssafy.user.bank.dto.response.SearchFromAccountResponse;
import com.ssafy.user.bank.dto.response.SearchToAccountResponse;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupMember.dto.response.AccountDTO;
import com.ssafy.user.groupMember.dto.response.QAccountDTO;
import com.ssafy.user.member.domain.QMember;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    public SearchToAccountResponse findToAccountByBankAndAccount(String bankName,
        String accountNumber) {
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
    public SearchFromAccountResponse findFromAccountByBankAndAccount(int accountId) {
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

    @Override
    public GetTransferListResponse findTransferByAccount(Account account) {
        QTransfer transfer = QTransfer.transfer;
        List<GetTransferListPerDateResponse> response = new ArrayList<>();
        LocalDateTime start = account.getCreatedAt();
        for (LocalDateTime date = LocalDateTime.now(); date.isAfter(start.minusDays(1));
            date = date.minusDays(1)) {
            List<GetTransferResponse> list = new ArrayList<>();
            List<GetTransferResponse> from = queryFactory
                .select(new QGetTransferResponse(
                    transfer.description,
                    transfer.createdAt,
                    transfer.amount,
                    Expressions.constant(false),
                    transfer.fromBalance
                ))
                .from(transfer)
                .where(transfer.fromAccount.eq(account)
                    .and(transfer.createdAt.year().eq(date.getYear()))
                    .and(transfer.createdAt.month().eq(date.getMonthValue()))
                    .and(transfer.createdAt.dayOfMonth().eq(date.getDayOfMonth())))
                .fetch();
            List<GetTransferResponse> to = queryFactory
                .select(new QGetTransferResponse(
                    transfer.description,
                    transfer.createdAt,
                    transfer.amount,
                    Expressions.constant(true),
                    transfer.toBalance
                    ))
                .from(transfer)
                .where(transfer.toAccount.eq(account)
                    .and(transfer.createdAt.year().eq(date.getYear()))
                    .and(transfer.createdAt.month().eq(date.getMonthValue()))
                    .and(transfer.createdAt.dayOfMonth().eq(date.getDayOfMonth())))
                .fetch();
            list.addAll(from);
            list.addAll(to);
            Collections.sort(list, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            if(list.size()==0) continue;
            response.add(new GetTransferListPerDateResponse(date.toLocalDate(), list));
        }
        return new GetTransferListResponse(response);
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
            .join(account.member, member)
                .fetchJoin()
            .where(account.isDeleted.eq(false)
                .and(account.accountId.eq(accountId).and(account.member.id.eq(memberId))))
            .fetchOne();
    }

}
