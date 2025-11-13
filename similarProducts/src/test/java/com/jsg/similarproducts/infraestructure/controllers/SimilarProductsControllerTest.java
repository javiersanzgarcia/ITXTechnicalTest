package com.jsg.similarproducts.infraestructure.controllers;

import com.jsg.similarproducts.application.ports.inbound.ProductsPort;
import com.jsg.similarproducts.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimilarProductsControllerTest {

    @Mock
    private ProductsPort productsPort;

    @InjectMocks
    private SimilarProductsController similarProductsController;

    @Test
    void getSimilarProducts_ShouldReturnProducts_WhenValidProductId() {
        Integer productId = 1;
        Product product2 = Product.builder()
                .id(2)
                .name("Product 2")
                .price(20.00)
                .availability(true)
                .build();
        Product product3 = Product.builder()
                .id(3)
                .name("Product 3")
                .price(30.00)
                .availability(true)
                .build();
        List<Product> mockProducts = Arrays.asList(product2, product3);

        when(productsPort.getSimilarProducts(1)).thenReturn(mockProducts);

        ResponseEntity<Object> response = similarProductsController.getSimilar(productId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(productsPort, times(1)).getSimilarProducts(1);
    }

    @Test
    void getSimilarProducts_ShouldReturnEmptyList_WhenNoSimilarProductsFound() {
        Integer productId = 999;
        when(productsPort.getSimilarProducts(999)).thenReturn(List.of());

        ResponseEntity<Object> response = similarProductsController.getSimilar(productId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getSimilarProducts_ShouldReturnBadRequest_WhenInvalidProductId() {
        Integer invalidProductId = -7;

        ResponseEntity<Object> response = similarProductsController.getSimilar(invalidProductId);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(productsPort, never()).getSimilarProducts(anyInt());
    }

}
