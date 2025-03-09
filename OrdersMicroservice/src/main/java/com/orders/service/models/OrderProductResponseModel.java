package com.orders.service.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderProductResponseModel extends Results{

    private String id;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private String status;
    private ProductResponseModel productDetails;

}
