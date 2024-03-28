package com.ssafy.user.bank.domain.repository;

import com.ssafy.user.bank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>, AccountRepositoryCustom {

}
