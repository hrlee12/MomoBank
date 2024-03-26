package com.ssafy.user.groupMember.domain;


import com.ssafy.user.common.BaseEntity;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inviteId;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    @Size(min = 0, max = 100)
    @Column(nullable = false, length = 100)
    private String identifier;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupInfo group;


    @Builder
    public Invite(LocalDateTime expireDate, String identifier, GroupInfo group){
        this.expireDate = expireDate;
        this.identifier = identifier;
        this.group = group;
    }
}
