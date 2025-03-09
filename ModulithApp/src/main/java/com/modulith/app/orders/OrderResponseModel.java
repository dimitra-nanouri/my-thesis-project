package com.modulith.app.orders;

import com.modulith.app.shared.Results;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResponseModel extends Results {

    private String id;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private String status;

}
