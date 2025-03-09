package com.modulith.app.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modulith.app.shared.ProductResponseModel;
import com.modulith.app.shared.Results;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderProductResponseModel extends Results {

    private String id;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private String status;
    @JsonProperty("productDetails")
    private ProductResponseModel productResponseModel;

}
