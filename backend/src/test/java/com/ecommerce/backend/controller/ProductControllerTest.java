package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private Product wirelessMouse;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productRepository.save(new Product(null, "Laptop", new BigDecimal("999.99")));
        wirelessMouse = productRepository.save(
                new Product(null, "Wireless Mouse", new BigDecimal("29.99"))
        );
        productRepository.save(new Product(null, "Mechanical Keyboard", new BigDecimal("79.99")));
    }

    @Test
    void returnsAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].name").value(containsInAnyOrder(
                        "Laptop",
                        "Wireless Mouse",
                        "Mechanical Keyboard"
                )));
    }

    @Test
    void returnsProductById() throws Exception {
        mockMvc.perform(get("/api/products/{id}", wirelessMouse.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(wirelessMouse.getId()))
                .andExpect(jsonPath("$.name").value("Wireless Mouse"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    void returnsNotFoundForUnknownProduct() throws Exception {
        mockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
}
