package com.ssafy.bank.account.domain;

import com.ssafy.bank.common.BaseTimeEntity;
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
@Table(name = "account_product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_product_id")
    private int accountProductId;

    @Column(name = "name")
    private String name;

    @Column(name = "interest_rate")
    private float interestRate;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "account_product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;
}
