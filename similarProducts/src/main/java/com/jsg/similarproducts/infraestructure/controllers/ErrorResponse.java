package com.jsg.similarproducts.infraestructure.controllers;

import lombok.Builder;

@Builder
public record ErrorResponse(String message) {
}
