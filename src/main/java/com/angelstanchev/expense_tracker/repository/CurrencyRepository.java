package com.angelstanchev.expense_tracker.repository;

import com.angelstanchev.expense_tracker.model.entity.CategoryEntity;
import com.angelstanchev.expense_tracker.model.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
}
