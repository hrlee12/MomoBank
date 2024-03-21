package com.ssafy.bank.account.domain;

import com.ssafy.bank.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AccountProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_product_id")
    private int accountProductId;

    @Column(length = 255, name = "name", nullable = false)
    private String name;

    @Column(length = 500, name = "description", nullable = false)
    private String description;

    @Column(name = "interest_rate", nullable = false)
    private float interestRate;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @OneToMany(mappedBy = "accountProduct", cascade = CascadeType.REFRESH)
    private List<Account> accounts;

    public enum AccountType{
        입출금자유예금, 정기예금, 적금
    }
}
