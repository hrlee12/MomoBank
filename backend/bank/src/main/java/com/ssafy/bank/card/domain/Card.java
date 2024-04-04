package com.ssafy.bank.card.domain;

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
@Table(name = "card")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private int cardId;

    @Column(length = 255, name = "card_name", nullable = false)
    private String cardName;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REFRESH)
    private List<CardProduct> cardProducts;
}
