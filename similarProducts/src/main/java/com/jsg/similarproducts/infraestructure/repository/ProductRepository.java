package com.jsg.similarproducts.infraestructure.repository;

import com.jsg.similarproducts.domain.model.Product;
import com.jsg.similarproducts.infraestructure.controllers.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepository {

    private final RestTemplate restTemplate;
    private final String apiBaseUrl;

    public ProductRepository(RestTemplate restTemplate,
                             @Value("${external.api.url}") String apiBaseUrl) {
        this.restTemplate = restTemplate;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<Integer> getSimilarProductIds(String productId) {
        String url = apiBaseUrl + "/product/" + productId + "/similarids";

        try {
            Integer[] ids = restTemplate.getForObject(url, Integer[].class);
            return ids != null ? Arrays.asList(ids) : Collections.emptyList();
        } catch (RestClientException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ErrorResponse.builder()
                            .message("Error retrieving similar product IDs: " + e.getMessage())
                            .build()
                            .message()
            );
        }
    }

    public Product getProductById(String productId) {
        String url = apiBaseUrl + "/product/" + productId;

        try {
            return restTemplate.getForObject(url, Product.class);
        } catch (RestClientException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ErrorResponse.builder()
                            .message("Error retrieving product with ID " + productId + ": " + e.getMessage())
                            .build()
                            .message()
            );
        }
    }
}
