package com.angelstanchev.expense_tracker.repository;

import com.angelstanchev.expense_tracker.model.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
}
