package com.ssafy.gateway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private int groupInfoId;

    @Column(length = 255, name = "group_name")
    private String groupName;

    @Column(length = 500, name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Member member;

    @OneToMany(mappedBy = "groupInfo", cascade = CascadeType.REFRESH)
    private List<GroupMember> groupMembers;


}

