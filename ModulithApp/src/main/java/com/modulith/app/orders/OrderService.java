package com.modulith.app.orders;

import java.util.List;

interface OrderService {
    List<OrderResponseModel> getAllOrders();
    OrderProductResponseModel getOrderDetailsById(String id);
    OrderProductResponseModel createNewOrder(OrderRequestModel orderDetails);
    OrderResponseModel completedOrder(String id);
}
