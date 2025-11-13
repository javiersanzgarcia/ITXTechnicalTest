package com.jsg.similarproducts.infraestructure.adapters.outbound;

import com.jsg.similarproducts.domain.model.Product;
import com.jsg.similarproducts.infraestructure.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryAdapterTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductRepositoryAdapter productRepositoryAdapter;

    @Test
    void findProducts_ShouldReturnListOfProducts_WhenValidProductId() {
        Integer productId = 1;
        List<Integer> similarIds = Arrays.asList(2, 3, 4);
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
        Product product4 = Product.builder()
                .id(4)
                .name("Product 4")
                .price(40.00)
                .availability(false)
                .build();

        when(productRepository.getSimilarProductIds(productId.toString())).thenReturn(similarIds);
        when(productRepository.getProductById("2")).thenReturn(product2);
        when(productRepository.getProductById("3")).thenReturn(product3);
        when(productRepository.getProductById("4")).thenReturn(product4);

        List<Product> result = productRepositoryAdapter.findProducts(productId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Product 2", result.get(0).getName());
        assertEquals("Product 3", result.get(1).getName());
        assertEquals("Product 4", result.get(2).getName());

        verify(productRepository, times(1)).getSimilarProductIds(productId.toString());
        verify(productRepository, times(1)).getProductById("2");
        verify(productRepository, times(1)).getProductById("3");
        verify(productRepository, times(1)).getProductById("4");
    }

    @Test
    void findProducts_ShouldReturnEmptyList_WhenNoSimilarProductsFound() {
        Integer productId = 1;
        when(productRepository.getSimilarProductIds(productId.toString())).thenReturn(List.of());

        List<Product> result = productRepositoryAdapter.findProducts(productId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).getSimilarProductIds(productId.toString());
        verify(productRepository, never()).getProductById(anyString());
    }

    @Test
    void findProducts_ShouldHandleErrorWhenFetchingProductDetails() {
        Integer productId = 1;
        List<Integer> similarIds = Arrays.asList(2, 3);
        Product product2 = Product.builder()
                .id(2)
                .name("Product 2")
                .price(20.00)
                .availability(true)
                .build();

        when(productRepository.getSimilarProductIds(productId.toString())).thenReturn(similarIds);
        when(productRepository.getProductById("2")).thenReturn(product2);
        when(productRepository.getProductById("3")).thenThrow(new RuntimeException("Error fetching product"));

        List<Product> result = productRepositoryAdapter.findProducts(productId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product 2", result.getFirst().getName());
    }
}
