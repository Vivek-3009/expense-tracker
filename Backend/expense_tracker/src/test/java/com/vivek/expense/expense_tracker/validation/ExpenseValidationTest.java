package com.vivek.expense.expense_tracker.validation;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFailForNegativeAmount() throws Exception {

        String requestBody = """
        {
          "amount": -100,
          "category": "Food",
          "date": "2026-04-24"
        }
        """;

        mockMvc.perform(post("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailWhenCategoryIsMissing() throws Exception {

        String requestBody = """
        {
          "amount": 100,
          "date": "2026-04-24"
        }
        """;

        mockMvc.perform(post("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}