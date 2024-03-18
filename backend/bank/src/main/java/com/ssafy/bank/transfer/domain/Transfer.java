package com.ssafy.bank.transfer.domain;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transfer")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private int transferId;

    @Column(name = "amount")
    private long amount;

    @Column(length = 255, name = "description")
    private String description;

    @Column(name = "from_balance")
    private long fromBalance;

    @Column(name = "to_balance")
    private long toBalance;

    @ManyToOne
    @JoinColumn(name = "from_account")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account")
    private Account toAccount;
}
