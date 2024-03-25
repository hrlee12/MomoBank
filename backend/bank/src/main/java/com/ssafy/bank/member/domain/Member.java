package com.ssafy.bank.member.domain;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @Column(nullable = false)
    private LocalDateTime birthDate;

    @Size(min = 0, max = 50)
    @Column(length = 50)
    @ColumnDefault("\"momo\"")
    private String provider;

    @ColumnDefault("50")
    private int sincerity;


    @OneToMany(mappedBy = "member", cascade = CascadeType.REFRESH)
    private List<Account> accounts;

    public void changeProvider() {
        this.provider = "kakao";
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void changePhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}


    @Builder
    public Member(String id, String name, String password, String phoneNumber,
                  LocalDateTime birthDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.sincerity = 50;
    }


}
