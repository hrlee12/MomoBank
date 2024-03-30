package com.ssafy.bank.account.domain.repository;

import com.ssafy.bank.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>, AccountRepositoryCustom {

}