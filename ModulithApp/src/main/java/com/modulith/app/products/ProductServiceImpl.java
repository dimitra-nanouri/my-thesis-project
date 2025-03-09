package com.modulith.app.products;

import com.modulith.app.shared.ProductResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
class ProductServiceImpl implements ProductService, ProductApi {

    private final ProductsRepository productsRepository;

    @Override
    public List<ProductResponseModel> getAllProducts() {
        log.info("Entering getAllProducts method");
        log.info("Retrieving products from database");
        Iterable<ProductEntity> entities = productsRepository.findAll();
        log.info("Products retrieved successfully");
        List <ProductResponseModel> products = new ArrayList<>();
        for (ProductEntity entity:entities) {
            products.add(new ModelMapper().map(entity, ProductResponseModel.class));
        }
        log.info("Exiting getAllProducts method");
        return products;
    }

    @Override
    public ProductResponseModel getProductById(String id) {
        log.info("Entering getProductById method");
        ProductResponseModel productResponseModel = new ProductResponseModel();
        try {
            log.info("Retrieving product details from database");
            ProductEntity productEntity = productsRepository.findById(id);
            log.info("Successfully retrieved order details");
            productResponseModel = new ModelMapper().map(productEntity,ProductResponseModel.class);
        } catch (Exception e){
            log.info("Couldn't find product with id = {} in database", id);
            productResponseModel.setMessage("Couldn't find product with id = "+id);
        }
        log.info("Exiting getProductById method");
        return productResponseModel;
    }

    @Override
    public ProductResponseModel createNewProduct(RequestProductDetailsModel productDetailsModel) {
        log.info("Entering createNewProduct method");
        String id = UUID.randomUUID().toString();
        ProductEntity productEntity = new ModelMapper().map(productDetailsModel, ProductEntity.class);
        productEntity.setId(id);
        productsRepository.save(productEntity);
        return new ModelMapper().map(productEntity , ProductResponseModel.class);
    }

    @Override
    public ProductResponseModel updateProductDetails(RequestProductDetailsModel productDetailsModel, String id) {
        log.info("Entering updateProductDetails method");
        ProductResponseModel productResponseModel = new ProductResponseModel();
        try {
            log.info("Entering findById method with id={}", id);
            ProductEntity productEntity = productsRepository.findById(id);
            log.info("Exiting findById method");
            productEntity.setName(productDetailsModel.getName());
            productEntity.setPrice(productDetailsModel.getPrice());
            productEntity.setStock(productDetailsModel.getStock());
            productEntity.setDetails(productDetailsModel.getDetails());
            log.info("Saving updated product");
            productsRepository.save(productEntity);
            log.info("Updated product saved");
            productResponseModel = new ModelMapper().map(productEntity,ProductResponseModel.class);
        } catch (Exception e) {
            log.info("Couldn't retrieve product with id = {} from database", id);
            productResponseModel.setMessage("Couldn't find product with id = "+id);
        }
        log.info("Exiting updateProductDetails method");
        return productResponseModel;
    }

    @Override
    public ProductResponseModel deleteProductEntity(String id) {
        log.info("Entering deleteProductEntity method with id={}", id);
        ProductResponseModel productResponseModel = new ProductResponseModel();
        try {
            log.info("Entering findById method with id={}", id);
            ProductEntity productEntity = productsRepository.findById(id);
            log.info("Exiting findById method.");
            if (productEntity.getId() != null) {
                log.info("Deleting product");
                productsRepository.delete(productEntity);
                log.info("Product deleted. Exiting deleteProductEntity method");
            }
            productResponseModel.setMessage("Product with id = " +id+ " deleted successfully");
        } catch (Exception e) {
            log.error("Product with id={} not found", id);
            productResponseModel.setMessage("Product with id = " +id+ " not found");
        }

        return productResponseModel;
    }

    @Override
    public ProductResponseModel reduceStock(String productId, int quantity) {
        log.info("Entering reduceStock method");
        ProductResponseModel productResponseModel = new ProductResponseModel();
        try {
            log.info("Entering findById method with id = {}", productId);
            ProductEntity productEntity = productsRepository.findById(productId);
            log.info("Exiting findById method ");
            productEntity.setStock(productEntity.getStock() - quantity);
            log.info("Saving updated stock");
            productsRepository.save(productEntity);
            log.info("Updated stock saved");
            productResponseModel = new ModelMapper().map(productEntity, ProductResponseModel.class);

        } catch (Exception e) {
            log.info("Couldn't find product with id = {} from database", productId);
            productResponseModel.setMessage("Couldn't find product with id = "+productId);
        }
        return productResponseModel;
    }

}
