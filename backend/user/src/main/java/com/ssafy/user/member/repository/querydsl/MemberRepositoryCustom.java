package com.ssafy.user.member.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.entity.QAccount;
import com.ssafy.user.member.dto.response.MemberDTO;
import com.ssafy.user.member.dto.response.MemberToCheckDTO;
import com.ssafy.user.member.dto.response.QMemberDTO;
import com.ssafy.user.member.dto.response.QMemberToCheckDTO;
import com.ssafy.user.member.entity.Member;
import com.ssafy.user.member.entity.QMember;
import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QMember member = QMember.member;

    private QAccount account = QAccount.account;


    public Member findMemberByIdAndPhoneNumber(String id, String phoneNumber) {
        return queryFactory.select(member)
                .from(member)
                .leftJoin(member.accounts, account)
                .fetchJoin()
                .where(member.id.eq(id).and(member.phoneNumber.eq(phoneNumber)))
                .fetchOne();
    }



    public Member findMemberByIdAndPassword(String id, String password) {
        return queryFactory.select(member)
                .from(member)
                .leftJoin(member.accounts, account)
                .fetchJoin()
                .where(member.id.eq(id).and(member.password.eq(password)))
                .fetchOne();

    }


    public Member findMemberById(String id){
        return queryFactory.select(member)
                .from(member)
                .leftJoin(member.accounts, account)
                .fetchJoin()
                .where(member.id.eq(id))
                .fetchOne();
    }



    public MemberDTO findMemberDtoById(String id){
        return queryFactory.select(new QMemberDTO(member.id, member.email, member.name, member.phoneNumber, member.birthDate, member.createdAt))
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
    }



    public MemberToCheckDTO findMemberToCheckDtoByPhoneNumber(String phoneNumber) {
        return queryFactory.select(new QMemberToCheckDTO(member.id))
                .from(member)
                .where(member.phoneNumber.eq(phoneNumber))
                .fetchOne();
    }


    public MemberToCheckDTO findMemberToCheckDtoById(String id){
        return queryFactory.select(new QMemberToCheckDTO(member.id))
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
    }


}
