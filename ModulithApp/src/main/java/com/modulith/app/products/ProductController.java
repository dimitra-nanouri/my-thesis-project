package com.modulith.app.products;

import com.modulith.app.shared.ProductResponseModel;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
class ProductController {

    @Autowired
    private ProductService productService;

    //User endpoints

    @GetMapping(path = "/eshop/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductResponseModel> getAllProducts () {
        log.info("Entering getAllProducts method");
        return productService.getAllProducts();
    }

    @GetMapping(path = "/eshop/products/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseModel getProductDetails (@PathVariable ("id") String id) {
        log.info("Entering getProductDetails method with id={}",id);
        return productService.getProductById(id);
    }

    //Admin endpoints

    @PostMapping(path = "/eshop-management/new-product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseModel createProduct (@Valid @RequestBody RequestProductDetailsModel productDetailsModel) {
        log.info("Entering createProduct method");
        return productService.createNewProduct(productDetailsModel);
    }

    @PutMapping(path = "/eshop-management/update-product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseModel updateProductDetails (@Valid @RequestBody RequestProductDetailsModel productDetailsModel, @PathVariable("id") String id) {
        log.info("Entering updateProductDetails method");
        return productService.updateProductDetails(productDetailsModel, id);
    }

    @DeleteMapping (path = "/eshop-management/delete-product/{id}")
    public ProductResponseModel deleteProduct (@PathVariable("id") String id) {
        log.info("Entering deleteProduct method");
        return productService.deleteProductEntity(id);
    }
}
