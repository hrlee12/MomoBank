package com.ssafy.user.bank.entity.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.entity.Account;
import com.ssafy.user.bank.entity.QAccount;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupMember.dto.response.AccountDTO;
import com.ssafy.user.groupMember.dto.response.QAccountDTO;
import com.ssafy.user.member.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QAccount account = QAccount.account;
    private final QGroupInfo group = QGroupInfo.groupInfo;
    private final QMember member = QMember.member;

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


    public Account findbyIdAndMemberId(int accountId, String memberId) {
        return queryFactory.select(account)
                .from(account)
                .join(account.groupInfo, group)
                .fetchJoin()
                .join(account.member, member)
                .where(account.isDeleted.eq(false).and(account.accountId.eq(accountId).and(account.member.id.eq(memberId))))
                .fetchOne();
    }

}
