package com.products.service.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductResponseModel extends Results {
    private String id;
    private String name;
    private BigDecimal price;
    private int stock;
    private String details;

}
