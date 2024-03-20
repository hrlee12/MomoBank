package com.ssafy.bank.card.domain.repository;

import com.ssafy.bank.card.domain.CardProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardProductRepository extends JpaRepository<CardProduct, Integer> {

}
