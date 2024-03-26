package com.ssafy.user.groupInfo.domain;

import com.ssafy.user.bank.entity.Account;
import com.ssafy.user.budget.entity.Budget;
import com.ssafy.user.common.BaseEntity;
import com.ssafy.user.groupMember.domain.GroupMember;
import jakarta.persistence.*;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_info")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_info_id")
    private int groupId;

    @Column(length = 255, name = "group_name")
    private String groupName;

    @Column(length = 500, name = "description")
    private String description;

    @Column(name = "created_by")
    private int createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "groupInfo", cascade = CascadeType.REFRESH)
    private List<Budget> budgets;

    @OneToMany(mappedBy = "groupInfo", cascade = CascadeType.REFRESH)
    private List<GroupMember> groupMembers;
}
