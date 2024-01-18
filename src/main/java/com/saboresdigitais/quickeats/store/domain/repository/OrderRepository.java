package com.saboresdigitais.quickeats.store.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saboresdigitais.quickeats.store.domain.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Métodos adicionais conforme necessário, por exemplo:
    // List<Order> findByCustomer(Customer customer);
}
