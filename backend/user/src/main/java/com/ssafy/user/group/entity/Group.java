package com.ssafy.user.group.entity;

import com.ssafy.user.bank.entity.Account;
import com.ssafy.user.common.BaseEntity;
import com.ssafy.user.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "group")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(length = 255, name = "group_name")
    private String groupName;

    @Column(length = 500, name = "description")
    private String description;

    @Column(name = "created_by")
    private int createdBy;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
