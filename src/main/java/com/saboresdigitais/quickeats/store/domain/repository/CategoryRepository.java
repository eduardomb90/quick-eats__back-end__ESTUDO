package com.saboresdigitais.quickeats.store.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saboresdigitais.quickeats.store.domain.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Métodos adicionais conforme necessário
}
