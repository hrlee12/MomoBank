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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int memberId;

    @Column(length = 255, name = "id", nullable = false)
    private String id;

    @Column(length = 255, name = "email")
    private String email;

    @Column(length = 255, name = "password", nullable = false)
    private String password;

    @Column(length = 20, name = "name", nullable = false)
    private String name;

    @Column(length = 50, name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "sincerity", nullable = false)
    private int sincerity;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REFRESH)
    private List<Account> accounts;
}
