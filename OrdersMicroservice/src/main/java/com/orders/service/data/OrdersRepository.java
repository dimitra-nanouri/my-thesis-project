package com.orders.service.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends MongoRepository<OrderEntity,Long> {
    OrderEntity getOrderById(String id);

}
