package com.ssafy.user.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.domain.QAccount;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.groupMember.domain.QGroupMember;
import com.ssafy.user.member.domain.QMember;
import com.ssafy.user.member.dto.response.*;
import com.ssafy.user.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QMember member = QMember.member;

    private QAccount account = QAccount.account;
    private QGroupInfo groupInfo = QGroupInfo.groupInfo;
    private QGroupMember groupMember = QGroupMember.groupMember;


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

    public Member findMemberBankHomeInfoByMemberId(String memberId) {

        return queryFactory.select(member)
                .from(member)
                .where(member.id.eq(memberId).and(member.isDeleted.eq(false)))
                .leftJoin(member.groupMembers, groupMember)
                .fetchJoin()
                .leftJoin(groupMember.groupInfo, groupInfo)
                .fetchJoin()
                .leftJoin(member.accounts, account)
                .fetchJoin()
                .fetchOne();
    }

}