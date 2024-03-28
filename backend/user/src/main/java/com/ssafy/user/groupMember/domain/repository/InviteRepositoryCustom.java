package com.ssafy.user.groupMember.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.user.bank.entity.QAccount;
import com.ssafy.user.groupInfo.domain.QGroupInfo;
import com.ssafy.user.groupMember.domain.Invite;
import com.ssafy.user.groupMember.domain.QInvite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InviteRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private final QInvite invite = QInvite.invite;
    private final QGroupInfo group = QGroupInfo.groupInfo;

    public List<Invite> findByGroupInfoIdAndExpiredDate(int groupId, LocalDateTime date) {
        return jpaQueryFactory.select(invite)
                .from(invite)
                .where(invite.isDeleted.eq(false).and(invite.group.groupInfoId.eq(groupId).and(invite.expireDate.after(date))))
                .fetch();
    }


}
