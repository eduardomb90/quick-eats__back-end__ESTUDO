package com.saboresdigitais.quickeats.store.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saboresdigitais.quickeats.store.domain.entity.Product;
import com.saboresdigitais.quickeats.store.domain.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product){
        // Adicionar regra de negócio
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails){
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado para o id :: " + id));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado para o id :: " + id));
        
        productRepository.delete(product);
    }
}
