package com.jsg.similarproducts.infraestructure.adapters.outbound;

import com.jsg.similarproducts.application.ports.outbound.ProductsOutPort;
import com.jsg.similarproducts.domain.model.Product;
import com.jsg.similarproducts.infraestructure.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProductRepositoryAdapter implements ProductsOutPort {

    private final ProductRepository productRepository;

    public ProductRepositoryAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findProducts(Integer productId) {
        try {
            List<Integer> productIds = productRepository.getSimilarProductIds(productId.toString());
            return productIds.stream()
                    .map(id -> {
                        try {
                            return productRepository.getProductById(id.toString());
                        } catch (Exception e) {
                            System.err.println("Error fetching product " + id + ": " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching similar product IDs: " + e.getMessage(), e);
        }
    }
}
