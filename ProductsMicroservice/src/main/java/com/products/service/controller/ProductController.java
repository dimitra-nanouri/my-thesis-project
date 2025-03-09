package com.products.service.controller;

import com.products.service.models.ProductResponseModel;
import com.products.service.models.RequestProductDetailsModel;
import com.products.service.service.ProductsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class ProductController {

    @Autowired
    private ProductsService productsService;

    //User endpoints

    @GetMapping(path = "/eshop/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductResponseModel> getAllProducts () {
        log.info("Entering getAllProducts method");
        return productsService.getAllProducts();
    }

    @GetMapping(path = "/eshop/products/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseModel getProductDetails (@PathVariable ("id") String id) {
        log.info("Entering getProductDetails method with id={}",id);
        return productsService.getProductById(id);
    }

    //Admin endpoints

    @PostMapping(path = "/eshop-management/new-product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseModel createProduct (@Valid @RequestBody RequestProductDetailsModel productDetailsModel) {
        log.info("Entering createProduct method");
        return productsService.createNewProduct(productDetailsModel);
    }

    @PutMapping(path = "/eshop-management/update-product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseModel updateProductDetails (@Valid @RequestBody RequestProductDetailsModel productDetailsModel, @PathVariable("id") String id) {
        log.info("Entering updateProductDetails method");
        return productsService.updateProductDetails(productDetailsModel, id);
    }

    @PutMapping(path = "/eshop-management/{id}/reduce-stock", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseModel reduceStock(@PathVariable("id") String id, @RequestParam int quantity) {
        log.info("Entering reduceStock method");
        return productsService.reduceStock(id,quantity);
    }

    @DeleteMapping (path = "/eshop-management/delete-product/{id}")
    public ProductResponseModel deleteProduct (@PathVariable("id") String id) {
        log.info("Entering deleteProduct method");
        return productsService.deleteProductEntity(id);
    }
}
