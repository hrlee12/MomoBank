package com.ssafy.bank.account.domain;

import com.ssafy.bank.card.domain.CardInfo;
import com.ssafy.bank.common.BaseTimeEntity;
import com.ssafy.bank.member.domain.Member;
import com.ssafy.bank.transfer.domain.Transfer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "balance")
    private long balance;

    @ManyToOne
    @JoinColumn(name = "account_product_id")
    private AccountProduct accountProduct;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REFRESH)
    private List<CardInfo> cardInfos;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.REFRESH)
    private List<Transfer> fromTransfers;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.REFRESH)
    private List<Transfer> toTransfers;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
