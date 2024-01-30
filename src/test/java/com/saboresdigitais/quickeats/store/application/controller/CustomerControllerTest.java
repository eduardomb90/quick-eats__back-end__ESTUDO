package com.saboresdigitais.quickeats.store.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.saboresdigitais.quickeats.store.SaboresDigitaisApplication;
import com.saboresdigitais.quickeats.store.domain.dto.CustomerDTO;
import com.saboresdigitais.quickeats.store.domain.vo.Address;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

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

    // @Test //Retorna um Lista de Customer, não paginado
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
    public void shouldAddCustomer() throws Exception {
    // Given: Prepare a customer DTO object with the necessary data
        Address address = new Address("Rua dos Alfeneiros, n. 9, Apto 303 - Centro", "São Paulo", "SP", "01135577");
        CustomerDTO customerDTO = new CustomerDTO("Lily Doe", "lilydoe@example.com", "encryptedPassword",
                                                   address.getStreet(), address.getCity(), address.getState(), address.getCep());

        // When: Convert the customer DTO to JSON and send a POST request to the '/api/customers' endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
            // Then: Expect a 201 Created status and verify the response contains the expected data
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value(customerDTO.getName()))
            .andExpect(jsonPath("$.email").value(customerDTO.getEmail()));

        // Additionally: Verify that the customer is persisted in the database
        // This can be done by querying the database and asserting the returned data matches the posted data
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        Long customerIdToDelete = 1L; // Supondo que o cliente com ID 1 exista no banco de dados

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/" + customerIdToDelete))
                .andExpect(status().isOk())
                .andExpect(result -> assertNotNull(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.success", is(true))); // Supondo que seu endpoint retorna um objeto JSON com uma propriedade 'success' em caso de sucesso
    }


    //Métodos auxiliares
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
