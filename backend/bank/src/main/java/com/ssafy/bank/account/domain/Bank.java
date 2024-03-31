package com.ssafy.bank.account.domain;

import com.ssafy.bank.card.domain.CardProduct;
import com.ssafy.bank.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private int bankId;

    @Column(length = 255, name = "bank_name", nullable = false)
    private String bankName;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.REFRESH)
    private List<AccountProduct> accountProducts;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.REFRESH)
    private List<CardProduct> cardProducts;
}
