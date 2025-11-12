package com.jsg.similarproducts.application.ports.inbound;

import com.jsg.similarproducts.domain.model.Product;

import java.util.List;

public interface ProductsPort {
    List<Product> getSimilarProducts(Integer productId);
}
