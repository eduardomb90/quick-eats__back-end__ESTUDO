package com.saboresdigitais.quickeats.store.domain.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saboresdigitais.quickeats.store.domain.entity.Customer;
import com.saboresdigitais.quickeats.store.domain.repository.AddressRepository;
import com.saboresdigitais.quickeats.store.domain.repository.CustomerRepository;
import com.saboresdigitais.quickeats.store.domain.vo.Address;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Service
public class CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;

    public CustomerService(PasswordEncoder passwordEncoder, CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));

        customer.updateName(customerDetails.getName());
        customer.updateEmail(customerDetails.getEmail());
        // Atualize outros campos conforme necessário
        customerRepository.save(customer);
        return customer;
    }


    @Transactional
    public Customer registerCustomer(String name, String email, String rawPassword, Address address) {
        if (customerRepository.existsByEmail(email)) {
            // Lança uma exceção ou retorna algum tipo de erro que pode ser manipulado
            throw new IllegalStateException("Email já registrado.");
        }

        // Remove todos os caracteres não numéricos do CEP
        String cep = address.getCep().replaceAll("[^0-9]", "");

        // Verifica se o endereço já existe
        Optional<Address> existingAddress = addressRepository
                .findByStreetAndCityAndStateAndCep(address.getStreet(), address.getCity(), address.getState(), cep);

        Address managedAddress;
        if (!existingAddress.isPresent()) {
            // Salva o novo endereço se ele ainda não existe
            address.setCep(cep);
            managedAddress = addressRepository.save(address);
        } else {
            // Utiliza o endereço existente
            managedAddress = existingAddress.get();
        }

        String encryptedPassword = passwordEncoder.encode(rawPassword);
        Customer newCustomer = new Customer(name, email, encryptedPassword, managedAddress);
        return customerRepository.save(newCustomer);
    }

    @Transactional
    public Customer changeCustomerPassword(Long customerId, String rawPassword) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + customerId));

        // Codifica a nova senha
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Chama o método para atualizar a senha
        boolean passwordChanged = customer.changeCustomerPassword(encodedPassword);

        if (!passwordChanged) {
            throw new IllegalArgumentException("Nova senha não pode ser vazia.");
        }

        //customerRepository.save(customer);
        // Não é necessário chamar customerRepository.save(customer) pois a entidade já está sendo gerenciada
        return customer;
    }

    @Transactional
    public void changeCustomerAddress(Customer customer, Address newAddress) {
        // Remove todos os caracteres não numéricos do CEP
        String cep = newAddress.getCep().replaceAll("[^0-9]", "");

        // Verifica se o novo endereço já existe
        Optional<Address> existingAddress = addressRepository
                .findByStreetAndCityAndStateAndCep(newAddress.getStreet(), newAddress.getCity(), newAddress.getState(), cep);

        Address managedAddress;
        if (!existingAddress.isPresent()) {
            // Salva o novo endereço se ele ainda não existe
            newAddress.setCep(cep);
            managedAddress = addressRepository.save(newAddress);
        } else {
            // Utiliza o endereço existente
            managedAddress = existingAddress.get();
        }

        // Atualiza o endereço do cliente
        customer.updateAddress(managedAddress);
        customerRepository.save(customer);
    }

    @Transactional
    public Customer changeCustomerEmail(Long customerId, String newEmail) {
        if (newEmail == null || newEmail.isBlank() || !newEmail.contains("@")) {
            throw new IllegalArgumentException("Forneça um e-mail válido.");
        }

        if (customerRepository.existsByEmail(newEmail)) {
            throw new IllegalStateException("O e-mail fornecido já está em uso.");
        }

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + customerId));

        customer.updateEmail(newEmail);

        // Não é necessário chamar customerRepository.save(customer) pois a entidade já está sendo gerenciada
        return customer;
    }
}

