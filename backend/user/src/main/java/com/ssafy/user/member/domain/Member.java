package com.ssafy.user.member.domain;

import com.ssafy.user.bank.domain.Account;
import com.ssafy.user.common.BaseEntity;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.groupMember.domain.GroupMember;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

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
    private List<Account> accounts;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REFRESH)
    private List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REFRESH)
    private List<GroupInfo> groupInfos;

    public void changeProvider() {
        this.provider = "kakao";
    }

    public void changePhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}


    public void changePassword(String password) {
        this.password = password;
    }

    public void changeFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }


    @Builder
    public Member(int memberId, String id, String name, String password, String phoneNumber,
        LocalDateTime birthDate) {
        this.memberId = memberId;
        this.id = id;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.sincerity = 50;
    }


}
