package com.vivek.expense.expense_tracker.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExpenseResponse {

     private String id;
    private BigDecimal amount;
    private String category;
    private String description;
    private LocalDate date;
    private LocalDateTime createdAt;
}