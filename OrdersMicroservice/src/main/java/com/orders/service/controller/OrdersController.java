package com.orders.service.controller;

import com.orders.service.models.OrderProductResponseModel;
import com.orders.service.models.OrderRequestModel;
import com.orders.service.models.OrderResponseModel;
import com.orders.service.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class OrdersController {

    @Autowired
    private OrderService orderService;

    //User endpoints

    @GetMapping(path = "/eshop/order/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderProductResponseModel getOrderDetails (@PathVariable ("id") String id) {
        log.info("Entering getOrderDetails method");
        return orderService.getOrderDetailsById(id);
    }

    @PostMapping(path = "/eshop/place-order",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderProductResponseModel createOrder(@RequestBody OrderRequestModel orderDetails) {
        log.info("Entering createOrder method");
        return orderService.createNewOrder(orderDetails);
    }

    //Admin endpoints

    @GetMapping(path = "/eshop-management/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderResponseModel> getAllOrders() {
        log.info("Entering getAllOrders method");
        return orderService.getAllOrders();
    }

    @PutMapping("/eshop-management/orders/completed/{id}")
    public OrderResponseModel markOrderAsCompleted(@PathVariable ("id") String id) {
        return orderService.completedOrder(id);
    }
}
