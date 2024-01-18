package com.saboresdigitais.quickeats.store.domain.repository;

import com.saboresdigitais.quickeats.store.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Métodos adicionais conforme necessário
}