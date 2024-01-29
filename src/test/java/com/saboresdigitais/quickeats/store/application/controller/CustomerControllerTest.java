package com.saboresdigitais.quickeats.store.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.saboresdigitais.quickeats.store.SaboresDigitaisApplication;
import com.saboresdigitais.quickeats.store.infrastructure.config.SecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes={SaboresDigitaisApplication.class})
//@Import(SecurityConfig.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // @Test
    // public void shouldReturnListOfCustomers() throws Exception {
    //     mockMvc.perform(get("/api/customers"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
    //             .andExpect(jsonPath("$[0].name", is("Updated Name")));
    // }

    @Test
    public void shouldReturnPaginatedListOfCustomers() throws Exception {
        int page = 0;
        int size = 5;

        mockMvc.perform(get("/api/customers")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.content[0].name", is("Novo Customer")))
                .andExpect(jsonPath("$.totalPages", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)));
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        Long customerIdToDelete = 1L; // Supondo que o cliente com ID 1 exista no banco de dados

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/" + customerIdToDelete))
                .andExpect(status().isOk())
                .andExpect(result -> assertNotNull(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", is(true))); // Supondo que seu endpoint retorna um objeto JSON com uma propriedade 'success' em caso de sucesso
    }
}
