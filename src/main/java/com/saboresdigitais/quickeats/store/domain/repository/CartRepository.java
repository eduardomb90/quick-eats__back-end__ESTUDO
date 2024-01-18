package com.saboresdigitais.quickeats.store.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saboresdigitais.quickeats.store.domain.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Pode incluir métodos personalizados aqui
}
