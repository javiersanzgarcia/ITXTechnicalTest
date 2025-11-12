package com.jsg.similarproducts.application.ports.outbound;

import com.jsg.similarproducts.domain.model.Product;

import java.util.List;

public interface ProductsOutPort {
    List<Product> findProducts(Integer productId);
}
