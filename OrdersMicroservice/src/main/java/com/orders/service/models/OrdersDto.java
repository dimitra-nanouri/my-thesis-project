package com.orders.service.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdersDto {
    private String id;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private String status;

}
