package com.ssafy.user.groupMember.domain;

import com.ssafy.user.common.BaseEntity;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "group_member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private int groupMemberId;

    @Column(length = 50, name = "name", nullable = false)
    private String name;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private memberType role;

    @Column(name = "total_fee", nullable = false)
    @ColumnDefault("0")
    private long totalFee;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupInfo groupInfo;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public enum memberType {
        모임장, 모임원
    }
}
