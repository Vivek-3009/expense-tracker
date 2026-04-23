package com.vivek.expense.expense_tracker.reposityory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivek.expense.expense_tracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, String> {

    Optional<Expense> findByIdempotencyKey(String key);

    List<Expense> findByCategory(String category);
    
    List<Expense> findByCategoryStartingWithIgnoreCase(String category);
}