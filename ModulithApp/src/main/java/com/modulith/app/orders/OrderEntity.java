package com.modulith.app.orders;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import java.math.BigDecimal;

@Data
@Document(collection = "orders")
public class OrderEntity {

        @Id
        private String id;

        private String productId;

        private int quantity;

        @Field(targetType = FieldType.DECIMAL128)
        private BigDecimal price;

        private String status;
}
