package com.jsg.similarproducts.infraestructure.controllers;

import com.jsg.similarproducts.application.ports.inbound.ProductsPort;
import com.jsg.similarproducts.domain.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class SimilarProductsController {
    private final ProductsPort productsPort;

    @GetMapping("/{productId}/similar")
    public ResponseEntity<Object> getSimilar(@PathVariable Integer productId) {

        if (productId <= 0) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder()
                            .message("Invalid product ID")
                            .build());
        }

        List<Product> productList = productsPort.getSimilarProducts(productId);
        System.out.println(productList);
        if (productList == null || productList.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder()
                            .message("No similar products found for product ID: " + productId)
                            .build());
        }

        return ResponseEntity.ok(productList);
    }

}
