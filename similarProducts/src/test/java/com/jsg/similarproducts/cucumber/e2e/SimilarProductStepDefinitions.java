package com.jsg.similarproducts.cucumber.e2e;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
public class SimilarProductStepDefinitions extends CucumberSpringConfiguration {

    private ResponseEntity<Object> response;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Given("the application is running")
    public void theApplicationIsRunning() {
        assertNotNull(restTemplate, "RestTemplate should be initialized");
    }

    @When("the user requests the similar products for product ID {int}")
    public void theUserRequestsTheSimilarProductsForProductId(int productId) {
        try {
            String url = String.format("/product/%d/similar", productId);
            response = restTemplate.getForEntity(url, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to make request: " + e.getMessage(), e);
        }
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue(),
                String.format("Expected status code %d but was %d", statusCode, response.getStatusCodeValue()));
    }

    @Then("the response should contain a list of similar products")
    public void theResponseShouldContainAListOfSimilarProducts() throws Exception {
        assertNotNull(response.getBody(), "Response body should not be null");

        String responseBody;
        try {
            responseBody = objectMapper.writeValueAsString(response.getBody());
        } catch (Exception e) {
            responseBody = response.getBody().toString();
        }

        try {
            List<Map<String, Object>> products = objectMapper.readValue(
                    responseBody,
                    new TypeReference<>() {
                    }
            );
            assertFalse(products.isEmpty(), "The list of similar products should not be empty");

            // Validate each product in the list
            for (Map<String, Object> product : products) {
                assertTrue(product.containsKey("id"), "Product should have an 'id' field");
                assertTrue(product.containsKey("name"), "Product should have a 'name' field");
                assertTrue(product.containsKey("price"), "Product should have a 'price' field");
                assertTrue(product.containsKey("availability"), "Product should have an 'availability' field");
            }

        } catch (Exception e) {
            // If parsing as array fails, try to parse as a single product
            try {
                Map<String, Object> product = objectMapper.readValue(responseBody, new TypeReference<>() {
                });
                assertTrue(product.containsKey("id"), "Product should have an 'id' field");
                assertTrue(product.containsKey("name"), "Product should have a 'name' field");
                assertTrue(product.containsKey("price"), "Product should have a 'price' field");
                assertTrue(product.containsKey("availability"), "Product should have an 'availability' field");
            } catch (Exception ex) {
                // If both attempts fail, include the response body in the error message
                throw new AssertionError("Failed to parse response as list of products. Response: " + responseBody, ex);
            }
        }
    }
}
