package com.orders.service.service;

import com.orders.service.models.OrderProductResponseModel;
import com.orders.service.models.OrderRequestModel;
import com.orders.service.models.OrderResponseModel;
import java.util.List;

public interface OrderService {
    List<OrderResponseModel> getAllOrders();
    OrderProductResponseModel getOrderDetailsById(String id);
    OrderProductResponseModel createNewOrder(OrderRequestModel orderRequestModel);
    OrderResponseModel completedOrder(String id);
}
