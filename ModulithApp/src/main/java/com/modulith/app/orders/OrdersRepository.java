package com.modulith.app.orders;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OrdersRepository extends MongoRepository<OrderEntity, Long> {
    OrderEntity getOrderById(String id);
    List<OrderEntity> findByProductId(String productId);
}
