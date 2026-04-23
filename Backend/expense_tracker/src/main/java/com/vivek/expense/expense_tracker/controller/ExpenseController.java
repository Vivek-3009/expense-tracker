package com.vivek.expense.expense_tracker.controller;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.expense.expense_tracker.dto.CreateExpenseRequest;
import com.vivek.expense.expense_tracker.dto.ExpenseResponse;
import com.vivek.expense.expense_tracker.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/expenses")
@CrossOrigin
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ExpenseResponse create(
        @Valid @RequestBody CreateExpenseRequest request,
        @RequestHeader(value = "Idempotency-Key", required = false) String key) {
        return service.createExpense(request, key);
    }

    @GetMapping
    public List<ExpenseResponse> get(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String sort) {
        return service.getExpenses(category, sort);
    }
}