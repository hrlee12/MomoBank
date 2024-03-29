package com.ssafy.user.bank.domain.repository;

import com.ssafy.user.bank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>, AccountRepositoryCustom {
//    Optional<List<Account>> findByMember_memberId(int memberId);

}
