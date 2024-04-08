package com.ssafy.user.bank.domain.repository;

import com.ssafy.user.bank.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Integer> {

}
