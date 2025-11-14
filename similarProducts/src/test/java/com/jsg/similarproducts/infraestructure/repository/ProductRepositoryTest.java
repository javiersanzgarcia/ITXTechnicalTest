package com.jsg.similarproducts.infraestructure.repository;

import com.jsg.similarproducts.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private RestTemplate restTemplate;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        String apiBaseUrl = "http://test-api";
        productRepository = new ProductRepository(restTemplate, apiBaseUrl);
    }

    @Test
    void getSimilarProductIds_ShouldReturnListOfIds_WhenApiReturnsSuccess() {
        String productId = "1";
        String expectedUrl = "http://test-api/product/1/similarids";
        Integer[] mockResponse = {2, 3, 4};

        when(restTemplate.getForObject(eq(expectedUrl), eq(Integer[].class)))
                .thenReturn(mockResponse);

        List<Integer> result = productRepository.getSimilarProductIds(productId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(Arrays.asList(2, 3, 4), result);

        verify(restTemplate).getForObject(eq(expectedUrl), eq(Integer[].class));
    }

    @Test
    void getSimilarProductIds_ShouldThrowException_WhenApiFails() {
        String productId = "1";
        String expectedUrl = "http://test-api/product/1/similarids";
        String errorMessage = "500 Internal Server Error";

        when(restTemplate.getForObject(eq(expectedUrl), eq(Integer[].class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, errorMessage));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> productRepository.getSimilarProductIds(productId)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Error retrieving similar product IDs"));
        verify(restTemplate).getForObject(eq(expectedUrl), eq(Integer[].class));
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        String productId = "1";
        String expectedUrl = "http://test-api/product/1";
        Product mockProduct = Product.builder()
                .id(1)
                .name("Test Product")
                .price(10.00)
                .availability(true)
                .build();

        when(restTemplate.getForObject(eq(expectedUrl), eq(Product.class)))
                .thenReturn(mockProduct);

        Product result = productRepository.getProductById(productId);

        assertNotNull(result);
        assertEquals(Integer.parseInt(productId), result.getId());
        assertEquals("Test Product", result.getName());
        verify(restTemplate).getForObject(eq(expectedUrl), eq(Product.class));
    }

    @Test
    void getProductById_ShouldThrowException_WhenProductNotFound() {
        String productId = "999";
        String expectedUrl = "http://test-api/product/999";
        String errorMessage = "Product not found";

        when(restTemplate.getForObject(eq(expectedUrl), eq(Product.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, errorMessage));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> productRepository.getProductById(productId)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Error retrieving product with ID 999"));
        verify(restTemplate).getForObject(eq(expectedUrl), eq(Product.class));
    }
}
