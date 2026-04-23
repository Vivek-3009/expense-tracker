package com.vivek.expense.expense_tracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.expense.expense_tracker.dto.ExpenseResponse;
import com.vivek.expense.expense_tracker.reposityory.ExpenseRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @BeforeEach
    void setUp() {
        expenseRepository.deleteAll();
    }

    @Test
    void shouldNotCreateDuplicateExpenseWithSameIdempotencyKey() throws Exception {
        String requestBody = """
        {
          "amount": 100,
          "category": "Food",
          "description": "Lunch",
          "date": "2026-04-24"
        }
        """;

        String key = "test-key-123";

        // First Request - creates new expense
        MvcResult first = mockMvc.perform(post("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Idempotency-Key", key)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Second Request - same key should return existing expense
        MvcResult second = mockMvc.perform(post("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Idempotency-Key", key)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Deserialize and verify
        ExpenseResponse firstRes = objectMapper.readValue(first.getResponse().getContentAsString(), ExpenseResponse.class);
        ExpenseResponse secondRes = objectMapper.readValue(second.getResponse().getContentAsString(), ExpenseResponse.class);
        
        assertEquals(firstRes.id(), secondRes.id(), "IDs must match for idempotent requests");
        assertEquals(0, firstRes.amount().compareTo(secondRes.amount()), "Amounts must be equal");
        assertEquals(firstRes.category(), secondRes.category());
        assertEquals(firstRes.date(), secondRes.date());
    }
}