package com.saboresdigitais.quickeats.store.domain.service;

import org.springframework.stereotype.Service;

import com.saboresdigitais.quickeats.store.domain.entity.Cart;
import com.saboresdigitais.quickeats.store.domain.entity.CartItem;
import com.saboresdigitais.quickeats.store.domain.entity.Customer;
import com.saboresdigitais.quickeats.store.domain.entity.Product;
import com.saboresdigitais.quickeats.store.domain.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Cart createOrGetCart(Customer customer) {
        // Implementação para criar ou recuperar o carrinho existente de um cliente
        return new Cart();
    }

    @Transactional
    public Cart addItemToCart(Cart cart, Product product, int quantity) {
        // Implementação para adicionar um item ao carrinho
        return new Cart();
    }

    @Transactional
    public Cart removeItemFromCart(Cart cart, CartItem item) {
        // Implementação para remover um item do carrinho
        return new Cart();
    }
}
