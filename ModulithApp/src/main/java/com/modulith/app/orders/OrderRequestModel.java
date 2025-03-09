package com.modulith.app.orders;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequestModel {

    @NotNull(message = "ProductId cannot be null")
    private String productId;

    @NotNull(message = "Quantity cannot be null")
    @Positive (message = "Quantity cannot be a negative number")
    private int quantity;

}
