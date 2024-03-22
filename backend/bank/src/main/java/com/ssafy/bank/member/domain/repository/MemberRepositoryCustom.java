package com.ssafy.bank.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bank.account.domain.QAccount;
import com.ssafy.bank.member.domain.Member;
import com.ssafy.bank.member.domain.QMember;
import com.ssafy.bank.member.dto.response.MemberToCheckDTO;
import com.ssafy.bank.member.dto.response.QMemberToCheckDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QMember member = QMember.member;
    private QAccount account = QAccount.account;

    public MemberToCheckDTO findMemberToCheckDtoById(String id){
        return queryFactory.select(new QMemberToCheckDTO(member.id))
                .from(member)
                .where(member.id.eq(id).and(member.isDeleted.eq(false)))
                .fetchOne();
    }


    public Member findMemberById(String id){
        return queryFactory.select(member)
                .from(member)
                .leftJoin(member.accounts, account)
                .fetchJoin()
                .where(member.id.eq(id).and(member.isDeleted.eq(false)))
                .fetchOne();
    }



    public Member findMemberByIdAndPhoneNumber(String id, String phoneNumber) {
        return queryFactory.select(member)
                .from(member)
                .leftJoin(member.accounts, account)
                .fetchJoin()
                .where(member.id.eq(id).and(member.phoneNumber.eq(phoneNumber)).and(member.isDeleted.eq(false)))
                .fetchOne();
    }
}
