package com.ssafy.user.bank.entity.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.entity.QAccount;
import com.ssafy.user.groupMember.dto.response.AccountDTO;
import com.ssafy.user.groupMember.dto.response.QAccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QAccount account = QAccount.account;

    public List<AccountDTO> findAccountDTOByMemberId(int memberId) {

        return queryFactory.select(new QAccountDTO(account.accountId,
                                                    account.accountNumber,
                                                    account.accountProductName,
                                                    account.bankName,
                                                    account.balance))
                .from(account)
                .where(account.isDeleted.eq(false).and(account.member.memberId.eq(memberId)))
                .fetch();
    }

}
