package com.jsg.similarproducts.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    Integer id;
    String name;
    double price;
    boolean availability;
}
