package com.modulith.app.products;

import com.modulith.app.shared.ProductResponseModel;

import java.util.List;

interface ProductService {
    List <ProductResponseModel> getAllProducts();
    ProductResponseModel getProductById(String id);
    ProductResponseModel createNewProduct(RequestProductDetailsModel productDetailsModel);
    ProductResponseModel updateProductDetails (RequestProductDetailsModel productDetailsModel, String id);
    ProductResponseModel deleteProductEntity(String id);
}
