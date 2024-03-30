package com.ssafy.bank.account.domain.repository;

import com.ssafy.bank.account.domain.AccountProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountProductRepository extends JpaRepository<AccountProduct, Integer> {

}
