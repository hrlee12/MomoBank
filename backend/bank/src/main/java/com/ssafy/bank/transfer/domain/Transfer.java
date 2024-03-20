package com.ssafy.bank.transfer.domain;

import com.ssafy.bank.account.domain.Account;
import com.ssafy.bank.common.BaseEntity;
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
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "transfer")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private int transferId;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(length = 255, name = "description", nullable = false)
    private String description;

    @Column(name = "from_balance", nullable = false)
    private long fromBalance;

    @Column(name = "to_balance", nullable = false)
    private long toBalance;

    @ManyToOne
    @JoinColumn(name = "from_account", nullable = false)
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account", nullable = false)
    private Account toAccount;
}
