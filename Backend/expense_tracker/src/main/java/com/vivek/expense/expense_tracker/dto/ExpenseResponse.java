package com.vivek.expense.expense_tracker.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseResponse(

    String id,
    BigDecimal amount,
    String category,
    String description,
    LocalDate date,
    LocalDateTime createdAt)
{}