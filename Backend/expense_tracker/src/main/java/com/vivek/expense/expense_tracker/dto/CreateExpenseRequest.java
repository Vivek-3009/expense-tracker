package com.vivek.expense.expense_tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CreateExpenseRequest(

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    BigDecimal amount,

    @NotBlank(message = "Category is required")
    String category,

    String description,

    @NotNull(message = "Date is required")
    LocalDate date)
{}