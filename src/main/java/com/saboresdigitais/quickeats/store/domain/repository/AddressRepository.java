package com.saboresdigitais.quickeats.store.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saboresdigitais.quickeats.store.domain.vo.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
    Optional<Address> findByStreetAndCityAndStateAndCep(String street, String city, String state, String cep);
}
