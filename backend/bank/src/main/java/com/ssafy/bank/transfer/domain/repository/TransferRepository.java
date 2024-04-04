package com.ssafy.bank.transfer.domain.repository;

import com.ssafy.bank.transfer.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Integer>, TransferRepositoryCustom {

}
