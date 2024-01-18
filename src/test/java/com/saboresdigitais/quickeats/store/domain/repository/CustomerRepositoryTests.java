package com.saboresdigitais.quickeats.store.domain.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import com.saboresdigitais.quickeats.store.SaboresDigitaisApplication;
import com.saboresdigitais.quickeats.store.domain.entity.Customer;
import com.saboresdigitais.quickeats.store.domain.service.CustomerService;
import com.saboresdigitais.quickeats.store.domain.vo.Address;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest(classes = SaboresDigitaisApplication.class)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Customer customer;

    @BeforeEach
    public void setup() {
        // Inicialize o objeto Customer antes de cada teste

    }

    @Transactional // Esta anotação garantirá que cada teste seja executado dentro de uma transação.
    @Rollback(true) // Esta anotação garantirá que, após o teste, a transação seja revertida, limpando o banco de dados.
    @Test
    public void shouldSaveCustomerSuccessfully() {
        Address address = new Address("Rua dos Becos, n. 10, Apto 303 - Centro", "São Paulo", "SP", "01234567");
        Customer savedCustomer = customerService.registerCustomer("Customer Não Persistente", "quartotest@example.com", "s3nh@_De_TE$te", address);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
    }

    @Test
    public void shouldFindCustomerById() {
        Long existingCustomerId = 1L;

        // Busca o cliente existente no banco de dados pelo ID
        Customer existingCustomer = customerRepository.findById(existingCustomerId)
                                                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        // Verifica se o cliente foi encontrado
        assertThat(existingCustomer).isNotNull();
        assertThat(existingCustomer.getName()).isEqualTo("Updated Name");
    }

    @Transactional
    @Rollback(true)
    @Test
    public void shouldUpdateCustomerNameSuccessfully() {
        // Dado um cliente já salvo
        Customer savedCustomer = customerRepository.save(customer);

        // Quando atualizamos o nome
        String updatedName = "Updated Name";
        savedCustomer.updateName(updatedName);
        customerRepository.save(savedCustomer);

        // Então o nome deve ser atualizado no banco de dados
        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getName()).isEqualTo(updatedName);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void shouldUpdateCustomerEmailSuccessfully() {
        // Dado um cliente já salvo
        Customer savedCustomer = customerRepository.save(customer);

        // Quando atualizamos o email
        String updatedEmail = "updated@example.com";
        savedCustomer.updateEmail(updatedEmail);
        customerRepository.save(savedCustomer);

        // Então o email deve ser atualizado no banco de dados
        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getEmail()).isEqualTo(updatedEmail);
    }

    @Test
    public void shouldFailToRegisterCustomerWithExistingEmail() {
        // Dado um e-mail que já está cadastrado
        String existingEmail = "test@example.com";

        // Quando tentamos registrar um novo cliente com o mesmo e-mail
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            Address newAddress = new Address("Nova Rua dos Testes, 321", "Nova Test City", "NTC", "67890-123");
            customerService.registerCustomer("Another Test Customer", existingEmail, "novaSenha123", newAddress);
        });

        // Então uma exceção deve ser lançada
        String expectedMessage = "Email já registrado.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Uma exceção deve ser lançada informando que o e-mail já está registrado.");
    }

    @Transactional
    @Rollback(true)
    @Test
    public void shouldSuccessfullyUpdateCustomerEmail() {
        Long existingCustomerId = 1L; // ID de um cliente existente
        String newEmail = "updatedemail@example.com";

        customer = customerRepository.findById(existingCustomerId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        customerService.changeCustomerEmail(existingCustomerId, newEmail);

        Customer updatedCustomer = customerRepository.findById(existingCustomerId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado após atualização do e-mail"));

        assertThat(updatedCustomer.getEmail()).isEqualTo(newEmail);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void shouldFailToUpdateCustomerEmailWithExistingEmail() {
        Long existingCustomerId = 1L; // ID de um cliente existente
        String existingEmail = "terceitotest@example.com"; // E-mail já existente

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            customerService.changeCustomerEmail(existingCustomerId, existingEmail);
        });

        String expectedMessage = "O e-mail fornecido já está em uso.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Transactional
    @Rollback(true)
    @Test
    public void shouldFailToUpdateCustomerEmailWithInvalidEmail() {
        Long existingCustomerId = 1L; // ID de um cliente existente
        String invalidEmail = "invalidemail";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.changeCustomerEmail(existingCustomerId, invalidEmail);
        });

        String expectedMessage = "Forneça um e-mail válido.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldUpdateExistingCustomerNameSuccessfully() {
        // Supondo que o ID 1 pertence a um cliente existente
        Long existingCustomerId = 1L;

        // Busca o cliente existente no banco de dados
        Customer existingCustomer = customerRepository.findById(existingCustomerId)
                                                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        // Atualiza o nome do cliente
        String updatedName = "Novo Updated Name";
        existingCustomer.updateName(updatedName);

        // Salva as alterações no cliente
        customerRepository.save(existingCustomer);

        // Recupera o cliente atualizado do banco de dados
        Customer updatedCustomer = customerRepository.findById(existingCustomerId)
                                                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado após atualização"));

        // Verifica se o nome foi atualizado corretamente
        assertThat(updatedCustomer.getName()).isEqualTo(updatedName);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldUpdateCustomerAddressSuccessfully() {
        // Suponha que existe um Customer com ID 1
        Long existingCustomerId = 1L;

        // Busque o Customer existente
        Customer existingCustomer = customerRepository.findById(existingCustomerId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        // Crie um novo endereço e atualize o Customer
        Address newAddress = new Address("Nova Rua", "Nova Cidade", "Novo Estado", "12345678");
        customerService.changeCustomerAddress(existingCustomer, newAddress);

        // Após a atualização, recupere o Customer para verificar se o endereço foi atualizado
        Customer updatedCustomer = customerRepository.findById(existingCustomerId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado após atualização do endereço"));

        // Assertivas para verificar se o endereço foi atualizado
        assertThat(updatedCustomer.getAddress()).isNotNull();
        assertThat(updatedCustomer.getAddress().getStreet()).isEqualTo(newAddress.getStreet());
        assertThat(updatedCustomer.getAddress().getCity()).isEqualTo(newAddress.getCity());
        assertThat(updatedCustomer.getAddress().getState()).isEqualTo(newAddress.getState());
        assertThat(updatedCustomer.getAddress().getCep()).isEqualTo(newAddress.getCep());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void shouldUpdateExistingCustomerPasswordSuccessfully() {
        Long existingCustomerId = 1L;

        String newRawPassword = "novaSenha123";
        customerService.changeCustomerPassword(existingCustomerId, newRawPassword);

        Customer updatedCustomer = customerRepository.findById(existingCustomerId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + existingCustomerId));

        assertTrue(passwordEncoder.matches(newRawPassword, updatedCustomer.getPassword()),
                "A senha deve ser alterada e codificada corretamente.");
    }
}
