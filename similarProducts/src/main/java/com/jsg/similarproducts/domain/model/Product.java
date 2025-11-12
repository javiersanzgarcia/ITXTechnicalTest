package com.jsg.similarproducts.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Product {
    Integer id;
    String name;
    double price;
    boolean availability;
}
