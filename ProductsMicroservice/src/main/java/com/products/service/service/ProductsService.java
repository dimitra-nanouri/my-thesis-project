package com.products.service.service;

import com.products.service.models.ProductResponseModel;
import com.products.service.models.RequestProductDetailsModel;

import java.util.List;

public interface ProductsService {
    List<ProductResponseModel> getAllProducts();
    ProductResponseModel getProductById(String id);
    ProductResponseModel createNewProduct(RequestProductDetailsModel productDetailsModel);
    ProductResponseModel updateProductDetails (RequestProductDetailsModel productDetailsModel, String id);
    ProductResponseModel reduceStock(String id, int quantity);
    ProductResponseModel deleteProductEntity(String id);
}
