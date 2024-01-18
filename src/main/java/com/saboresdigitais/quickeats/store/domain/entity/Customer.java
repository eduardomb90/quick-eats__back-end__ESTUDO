package com.saboresdigitais.quickeats.store.domain.entity;

import com.saboresdigitais.quickeats.store.domain.vo.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório.")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Forneça um e-mail válido.")
    private String email;

    @NotBlank(message = "Senha é obrigatória.")
    private String password; // Note: This should be encrypted before persisting

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    public Customer(String name, String email, String encryptedPassword, Address address) {
        this.name = name;
        this.email = email;
        this.password = encryptedPassword; // Remember to encrypt the password
        this.address = address;
    }

    public boolean changeCustomerPassword(String encryptedPassword) {
        if (encryptedPassword != null && !encryptedPassword.isEmpty()) {
            this.password = encryptedPassword;
            return true; // Password changed successfully
        }
        return false; // Failed to change password
    }

    public void updateName(String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            this.name = newName;
        }
    }

    public void updateEmail(String newEmail) {
        if (newEmail != null && !newEmail.trim().isEmpty() && !newEmail.equals(this.email) && newEmail.contains("@")) {
            this.email = newEmail;
        }
    }

    public void updateAddress(Address newAddress) {
        if (newAddress != null &&
            newAddress.getStreet() != null && !newAddress.getStreet().isBlank() &&
            newAddress.getCity() != null && !newAddress.getCity().isBlank() &&
            newAddress.getState() != null && !newAddress.getState().isBlank() &&
            newAddress.getCep() != null && !newAddress.getCep().isBlank()) {

            this.address = newAddress;
        }
    }
}
