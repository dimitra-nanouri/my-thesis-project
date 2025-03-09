package com.products.service.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import java.math.BigDecimal;

@Data
@Document(collection = "products")
public class ProductEntity {

    @Id
    private String id;
    private String name;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;
    private int stock;
    private String details;

}
