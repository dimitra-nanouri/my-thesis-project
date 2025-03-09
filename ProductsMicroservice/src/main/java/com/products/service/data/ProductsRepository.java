package com.products.service.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends MongoRepository<ProductEntity,Long> {
    ProductEntity findById(String id);
}
