package com.jsg.similarproducts.application.service;

import com.jsg.similarproducts.application.ports.inbound.ProductsPort;
import com.jsg.similarproducts.application.ports.outbound.ProductsOutPort;
import com.jsg.similarproducts.domain.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements ProductsPort {

    private final ProductsOutPort productsoutPort;

    @Override
    public List<Product> getSimilarProducts(Integer productId) {
        return productsoutPort.findProducts(productId);
    }
}
