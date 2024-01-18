package com.saboresdigitais.quickeats.store.domain.repository;

import com.saboresdigitais.quickeats.store.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
