package com.ssafy.user.member.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.member.dto.response.MemberDTO;
import com.ssafy.user.member.dto.response.QMemberDTO;
import com.ssafy.user.member.entity.Member;
import com.ssafy.user.member.entity.QMember;
import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private QMember member = QMember.member;


    public List<Member> findMembers() {
        return queryFactory.select(member)
                .from(member)
                .fetch();
    }


    // 프로젝션 !
    // 특정 컬럼만 가져오기 !
    // 연관관계의 엔티티 불러오지 않음 !
    // 성능 최적화의 끝판왕
    public List<MemberDTO> findMembersDTO() {
        return queryFactory.select(new QMemberDTO(member.memberId, member.name, member.sincerity))
                .from(member)
                .fetch();
    }




}
