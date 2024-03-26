package com.ssafy.user.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.domain.QAccount;
import com.ssafy.user.member.domain.QMember;
import com.ssafy.user.member.dto.response.MemberDTO;
import com.ssafy.user.member.dto.response.MemberToCheckDTO;
import com.ssafy.user.member.dto.response.QMemberDTO;
import com.ssafy.user.member.dto.response.QMemberToCheckDTO;
import com.ssafy.user.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QMember member = QMember.member;

    private QAccount account = QAccount.account;


//    public Member findMemberByIdAndPhoneNumber(String id, String phoneNumber) {
//        return queryFactory.select(member)
//                .from(member)
//                .leftJoin(member.accounts, account)
//                .fetchJoin()
//                .where(member.id.eq(id).and(member.phoneNumber.eq(phoneNumber)).and(member.isDeleted.eq(false)))
//                .fetchOne();
//    }



    public Member findMemberByIdAndPassword(String id, String password) {
        return queryFactory.select(member)
            .from(member)
            .leftJoin(member.accounts, account)
            .fetchJoin()
            .where(member.id.eq(id).and(member.password.eq(password)).and(member.isDeleted.eq(false)))
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



    public MemberDTO findMemberDtoById(String id){
        return queryFactory.select(new QMemberDTO(member.id, member.name, member.phoneNumber, member.birthDate, member.createdAt))
            .from(member)
            .where(member.id.eq(id).and(member.isDeleted.eq(false)))
            .fetchOne();
    }



    public MemberToCheckDTO findMemberToCheckDtoByPhoneNumber(String phoneNumber) {
        return queryFactory.select(new QMemberToCheckDTO(member.id))
            .from(member)
            .where(member.phoneNumber.eq(phoneNumber).and(member.isDeleted.eq(false)))
            .fetchOne();
    }


    public MemberToCheckDTO findMemberToCheckDtoById(String id){
        return queryFactory.select(new QMemberToCheckDTO(member.id))
            .from(member)
            .where(member.id.eq(id).and(member.isDeleted.eq(false)))
            .fetchOne();
    }

    public MemberToCheckDTO findMemberToCheckDtoByIdAndPhoneNumber(String id, String phoneNumber){
        return queryFactory.select(new QMemberToCheckDTO(member.id))
            .from(member)
            .where(member.id.eq(id).and(member.phoneNumber.eq(phoneNumber).and(member.isDeleted.eq(false))))
            .fetchOne();
    }

}