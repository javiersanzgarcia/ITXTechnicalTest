Feature: Similar Products API
  As a client
  I want to get similar products
  So that I can see products related to a specific item

  @getSimilarProducts
  Scenario: Get similar products for a valid product ID
    Given the application is running
    When the user requests the similar products for product ID 1
    Then the response status code should be 200
    And the response should contain a list of similar products

  @getSimilarProducts
  Scenario: Handle non-existent product ID
    Given the application is running
    When the user requests the similar products for product ID 8
    Then the response status code should be 404

  @getSimilarProducts
  Scenario: Handle non-existent product ID
    Given the application is running
    When the user requests the similar products for product ID -3
    Then the response status code should be 404

  @getSimilarProducts
  Scenario: Get similar products for a valid product ID
    Given the application is running
    When the user requests the similar products for product ID 3
    Then the response status code should be 200
    And the response should contain a list of similar products
