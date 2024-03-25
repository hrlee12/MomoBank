package com.ssafy.user.bank.entity;

import com.ssafy.user.common.BaseEntity;
import com.ssafy.user.groupInfo.domain.GroupInfo;
import com.ssafy.user.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(length = 255, name = "account_number")
    private String accountNumber;

    @Column(length = 255, name = "account_product_name")
    private String accountProductName;

    @Column(length = 255, name = "bank_name")
    private String bankName;

    @Column(name = "interest_rate")
    private float interestRate;

    @Column(name = "balance")
    private long balance;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.REFRESH)
    private List<Transfer> fromTransfers;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.REFRESH)
    private List<Transfer> toTransfers;

    @OneToOne(mappedBy = "account")
    private GroupInfo groupInfo;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
