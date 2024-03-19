package com.ssafy.user.member.entity;

import com.ssafy.user.bank.entity.Account;
import com.ssafy.user.common.BaseEntity;
import com.ssafy.user.groupInfo.entity.GroupInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Size(min = 0, max = 100)
    @Column(nullable = false, length=100)
    private String id;

    @Size(min = 0, max = 100)
    @Column(length = 100)
    private String email;

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


    @Column(nullable = false)
    private String fcmToken;

    @Size(min = 0, max = 50)
    @Column(length = 50)
    @ColumnDefault("\"momo\"")
    private String provider;

    @ColumnDefault("50")
    private int sincerity;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REFRESH)
    private List<Account> accounts;

    public void addEmail(String email) {
        this.email = email;
    }

    public void changeProvider() {
        this.provider = "kakao";
    }


    @Builder
    public Member(String id, String name, String password, String phoneNumber, LocalDateTime birthDate, String fcmToken) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.fcmToken = fcmToken;
        this.sincerity = 50;
    }

}
