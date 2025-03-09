package com.orders.service.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequestModel {

    @NotNull(message = "ProductId cannot be null")
    private String productId;

    @NotNull(message = "Quantity cannot be null")
    @Positive
    private int quantity;

}
