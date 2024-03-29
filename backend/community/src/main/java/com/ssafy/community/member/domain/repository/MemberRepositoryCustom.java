package com.ssafy.community.member.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.community.feed.domain.Member;
import com.ssafy.community.feed.domain.QMember;
import com.ssafy.community.member.dto.MemberToCheckDTO;
import com.ssafy.community.member.dto.QMemberToCheckDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private QMember member = QMember.member;




    public Member findMemberById(String id){
        return queryFactory.select(member)
                .from(member)
                .where(member.id.eq(id).and(member.isDeleted.eq(false)))
                .fetchOne();
    }




    public MemberToCheckDTO findMemberToCheckDtoById(String id){
        return queryFactory.select(new QMemberToCheckDTO(member.id))
                .from(member)
                .where(member.id.eq(id).and(member.isDeleted.eq(false)))
                .fetchOne();
    }




}
