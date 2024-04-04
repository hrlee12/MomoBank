package com.ssafy.bank.card.domain.repository;

import com.ssafy.bank.card.domain.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardInfoRepository extends JpaRepository<CardInfo, Integer>,
    CardInfoRepositoryCustom {

}
