package com.orders.service.data;

import com.orders.service.models.ProductResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="ProductMicroservice",  url = "${productservice.url}")
public interface ProductServiceClient {

    @GetMapping("/eshop/products/{id}")
    ProductResponseModel getProductDetails (@PathVariable("id") String id);

    @PutMapping("/eshop-management/{id}/reduce-stock")
    ProductResponseModel reduceStock(@PathVariable("id") String id, @RequestParam int quantity);
}

