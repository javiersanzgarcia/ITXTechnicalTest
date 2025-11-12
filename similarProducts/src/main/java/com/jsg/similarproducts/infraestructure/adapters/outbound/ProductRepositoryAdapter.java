package com.jsg.similarproducts.infraestructure.adapters.outbound;

import com.jsg.similarproducts.application.ports.outbound.ProductsOutPort;
import com.jsg.similarproducts.domain.model.Product;
import com.jsg.similarproducts.infraestructure.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepositoryAdapter implements ProductsOutPort {

    private final ProductRepository productRepository;

    public ProductRepositoryAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findProducts(Integer productId) {
        List<Integer> productsIds = productRepository
                .getSimilarProductIds(productId.toString());
        return productsIds.stream()
                .map(id -> productRepository.getProductById(id.toString()))
                .toList();
    }
}
