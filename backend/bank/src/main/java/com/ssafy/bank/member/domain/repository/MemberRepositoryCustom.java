package com.ssafy.bank.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
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


    public MemberToCheckDTO findMemberToCheckDtoById(String id){
        return queryFactory.select(new QMemberToCheckDTO(member.id))
                .from(member)
                .where(member.id.eq(id).and(member.isDeleted.eq(false)))
                .fetchOne();
    }

}
