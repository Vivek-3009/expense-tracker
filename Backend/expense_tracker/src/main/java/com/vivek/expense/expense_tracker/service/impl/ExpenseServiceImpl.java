package com.vivek.expense.expense_tracker.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vivek.expense.expense_tracker.dto.CreateExpenseRequest;
import com.vivek.expense.expense_tracker.dto.ExpenseResponse;
import com.vivek.expense.expense_tracker.entity.Expense;
import com.vivek.expense.expense_tracker.exception.IdempotencyConflictException;
import com.vivek.expense.expense_tracker.reposityory.ExpenseRepository;
import com.vivek.expense.expense_tracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseServiceImpl(ExpenseRepository repo) {
        this.repo = repo;
    }

    @Override
    public ExpenseResponse createExpense(CreateExpenseRequest req, String key) {

        if (key != null) {
            Optional<Expense> existingOpt = repo.findByIdempotencyKey(key);

            if (existingOpt.isPresent()) {
                Expense existing = existingOpt.get();

                if (!isSameRequest(existing, req)) {
                    throw new IdempotencyConflictException(
                            "Request with same Idempotency-Key already exists with different data"
                    );
                }

                return mapToResponse(existing);
            }
        }

        Expense expense = Expense.builder()
                .id(UUID.randomUUID().toString())
                .amount(req.amount())
                .category(req.category())
                .description(req.description())
                .date(req.date())
                .createdAt(LocalDateTime.now())
                .idempotencyKey(key)
                .build();

        Expense saved = repo.save(expense);

        return mapToResponse(saved);
    }

    @Override
    public List<ExpenseResponse> getExpenses(String category, String sort) {

        List<Expense> expenses = (category != null)
                ? repo.findByCategory(category)
                : repo.findAll();

        if ("date_desc".equals(sort)) {
            expenses.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        }

        return expenses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private boolean isSameRequest(Expense e, CreateExpenseRequest req) {
        return Objects.equals(e.getAmount(), req.amount()) &&
               Objects.equals(e.getCategory(), req.category()) &&
               Objects.equals(e.getDescription(), req.description()) &&
               Objects.equals(e.getDate(), req.date());
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getDate(),
                expense.getCreatedAt()
        );
    }
}