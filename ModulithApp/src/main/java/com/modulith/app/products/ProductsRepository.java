package com.modulith.app.products;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProductsRepository extends MongoRepository<ProductEntity, Long> {
    ProductEntity findById(String id);
}
