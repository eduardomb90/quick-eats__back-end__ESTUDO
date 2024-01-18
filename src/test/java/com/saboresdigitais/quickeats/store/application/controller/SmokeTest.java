package com.saboresdigitais.quickeats.store.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private CustomerController customerController;

    @Test
    void contextLoads() throws Exception {
        assert(customerController != null);
    }
}
