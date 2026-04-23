package com.vivek.expense.expense_tracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

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


        MvcResult first = mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Idempotency-Key", key)
                .content(requestBody))
                .andReturn();


        MvcResult second = mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Idempotency-Key", key)
                .content(requestBody))
                .andReturn();

        assertEquals(
                first.getResponse().getContentAsString(),
                second.getResponse().getContentAsString()
        );
    }
}