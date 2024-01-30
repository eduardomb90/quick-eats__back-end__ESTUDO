package com.saboresdigitais.quickeats.store.domain.dto;

import com.saboresdigitais.quickeats.store.domain.entity.Customer;
import com.saboresdigitais.quickeats.store.domain.vo.Address;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    // Address information can be flat or nested as an Address DTO
    private String street;
    private String city;
    private String state;
    private String cep;

    // Constructors, getters, and setters
    public CustomerDTO() {}

    public CustomerDTO(String name, String email, String password, String street, String city, String state, String cep) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.street = street;
        this.city = city;
        this.state = state;
        this.cep = cep;
    }

    // Standard getters and setters @Data - lombok

    public Customer convertToEntity(CustomerDTO customerDTO) {
        // Conversion logic from DTO to Customer entity
        // Example conversion code, adjust according to your actual DTO and entity fields
        Address address = new Address(customerDTO.getStreet(), customerDTO.getCity(), customerDTO.getState(), customerDTO.getCep());
        return new Customer(customerDTO.getName(), customerDTO.getEmail(), customerDTO.getPassword(), address);
    }
}
