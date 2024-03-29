package com.ssafy.user.budget.domain.repository;

import com.ssafy.user.budget.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Integer>, BudgetRepositoryCustom {

}
