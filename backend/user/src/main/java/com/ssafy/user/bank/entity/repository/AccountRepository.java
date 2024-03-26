package com.ssafy.user.bank.entity.repository;

import com.ssafy.user.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<List<Account>> findByMember_memberId(int memberId);
}
