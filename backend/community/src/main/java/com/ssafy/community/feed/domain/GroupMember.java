package com.ssafy.community.feed.domain;


import com.ssafy.community.common.BaseEntity;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupInfo groupInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



    public enum memberType {
        모임장, 모임원
    }
}

