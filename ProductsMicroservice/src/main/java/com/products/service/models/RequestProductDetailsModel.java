package com.products.service.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestProductDetailsModel {

    @NotNull
    @Size(min=2, message = "Name cannot be less that two characters")
    private String name;

    @NotNull
    @Positive(message = "Stock must be greater than 0")
    private BigDecimal price;

    @NotNull
    @Positive(message = "Stock must be greater than 0")
    private int stock;

    @NotNull
    @Size(min=7, message = "Details cannot be less that two characters")
    private String details;

}
