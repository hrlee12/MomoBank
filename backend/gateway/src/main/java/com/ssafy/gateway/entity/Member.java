package com.ssafy.gateway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    private int memberId;

    @Size(min = 0, max = 100)
    @Column(nullable = false, length = 100)
    private String id;


    @Size(min = 0, max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @Size(min = 0, max = 100)
    @Column(nullable = false, length = 100)
    private String password;

    @Size(min = 0, max = 50)
    @Column(nullable = false, length = 50)
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime birthDate;

    private String fcmToken;

    @Size(min = 0, max = 50)
    @Column(length = 50)
    @ColumnDefault("\"momo\"")
    private String provider;

    @ColumnDefault("50")
    private int sincerity;


    @OneToMany(mappedBy = "member", cascade = CascadeType.REFRESH)
    private List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REFRESH)
    private List<GroupInfo> groupInfos;





}
