package com.jsg.similarproducts.infraestructure.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void createErrorResponse_ShouldSetMessage() {
        String errorMessage = "Test error message";
        ErrorResponse errorResponse = ErrorResponse.builder().message(errorMessage).build();
        assertNotNull(errorResponse);
        assertEquals(errorMessage, errorResponse.message());
    }

    @Test
    void errorResponse_ShouldBeImmutable() {
        String errorMessage = "Test error message";
        ErrorResponse errorResponse = ErrorResponse.builder().message(errorMessage).build();
        assertEquals(errorMessage, errorResponse.message(), "Message should remain unchanged");
    }

    @Test
    void errorResponse_ShouldHaveCorrectToString() {
        String errorMessage = "Test error message";
        ErrorResponse errorResponse = ErrorResponse.builder().message(errorMessage).build();
        String toString = errorResponse.toString();
        assertTrue(toString.contains(errorMessage));
    }
}
