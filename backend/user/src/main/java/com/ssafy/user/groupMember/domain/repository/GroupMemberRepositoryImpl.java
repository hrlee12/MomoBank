package com.ssafy.user.groupMember.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.groupMember.domain.GroupMember;
import com.ssafy.user.groupMember.domain.QGroupMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryImpl implements  GroupMemberRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private QGroupMember groupMember = QGroupMember.groupMember;

    List<GroupMember> findMyGroups(){
        return queryFactory.select(groupMember)
            .from(groupMember)
            .fetch();
    }
}
