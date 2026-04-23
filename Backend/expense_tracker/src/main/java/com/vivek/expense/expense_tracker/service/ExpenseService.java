package com.vivek.expense.expense_tracker.service;

import java.util.List;

import com.vivek.expense.expense_tracker.dto.CreateExpenseRequest;
import com.vivek.expense.expense_tracker.dto.ExpenseResponse;

public interface ExpenseService {

    ExpenseResponse createExpense(CreateExpenseRequest request, String idempotencyKey);

    List<ExpenseResponse> getExpenses(String category, String sort);
}