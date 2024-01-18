package com.saboresdigitais.quickeats.store.domain.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Rua é obrigatória.")
    @Size(max = 100)
    private String street;

    @NotBlank(message = "Cidade é obrigatória.")
    @Size(max = 50)
    private String city;

    @NotBlank(message = "Estado é obrigatório.")
    @Size(max = 50)
    private String state;

    @NotBlank(message = "CEP é obrigatório.")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter apenas números.")
    @Size(min = 8, max = 8)
    private String cep;

    public Address() {}

    public Address(String street, String city, String state, String cep) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.cep = cep;
    }

    public void updateAddress(String street, String city, String state, String cep) {
        if (street != null && !street.isBlank()) {
            this.street = street;
        }
        if (city != null && !city.isBlank()) {
            this.city = city;
        }
        if (state != null && !state.isBlank()) {
            this.state = state;
        }
        if (cep != null && !cep.isBlank()) {
            this.cep = cep;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (!street.toLowerCase().equals(address.street.toLowerCase())) return false;
        if (!city.toLowerCase().equals(address.city.toLowerCase())) return false;
        if (!state.toLowerCase().equals(address.state.toLowerCase())) return false;
        return cep.equals(address.cep);
    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + cep.hashCode();
        return result;
    }
}