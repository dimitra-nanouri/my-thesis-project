package com.modulith.app.products;

import com.modulith.app.shared.ProductResponseModel;

public interface ProductApi {
    ProductResponseModel getProductById(String id);
    ProductResponseModel reduceStock(String productId, int quantity);
}
