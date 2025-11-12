package com.jsg.similarproducts.infraestructure.repository;

import com.jsg.similarproducts.domain.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepository {

    private final RestTemplate restTemplate;

    public ProductRepository() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${external.api.url}")
    private String apiBaseUrl;

    // Retrieves a list of similar product IDs for a given product ID
    public List<Integer> getSimilarProductIds(String productId) {
        String url = apiBaseUrl + "/product/" + productId + "/similarids";

        try {
            Integer[] ids = restTemplate.getForObject(url, Integer[].class);
            return ids != null ? Arrays.asList(ids) : Collections.emptyList();
        } catch (RestClientException e) {
            throw new RuntimeException("Error retrieving similar product IDs: " + e.getMessage(), e);
        }
    }

    // Retrieves product details for a given product ID
    public Product getProductById(String productId) {
        String url = apiBaseUrl + "/product/" + productId;

        try {
            return restTemplate.getForObject(url, Product.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Error retrieving product with ID " + productId + ": " + e.getMessage(), e);
        }
    }
}
