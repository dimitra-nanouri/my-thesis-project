package com.orders.service.service;

import com.orders.service.data.OrderEntity;
import com.orders.service.data.OrdersRepository;
import com.orders.service.data.ProductServiceClient;
import com.orders.service.models.OrderProductResponseModel;
import com.orders.service.models.OrderRequestModel;
import com.orders.service.models.OrderResponseModel;
import com.orders.service.models.ProductResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrdersRepository ordersRepository;

    private ProductServiceClient productServiceClient;

    @Override
    public List<OrderResponseModel> getAllOrders() {
        log.info("Entering getAllProducts method");
        log.info("Retrieving products from database");
        Iterable<OrderEntity> entities = ordersRepository.findAll();
        List<OrderResponseModel> orders = new ArrayList<>();
        for (OrderEntity entity : entities) {
            orders.add(new ModelMapper().map(entity, OrderResponseModel.class));
        }
        return orders;
    }

    @Override
    public OrderProductResponseModel getOrderDetailsById(String id) {
        log.info("Entering getOrderDetailsById method with id={}", id);
        OrderProductResponseModel orderProductResponseModel = new OrderProductResponseModel();

        try {
            log.info("Retrieving order from database");
            OrderEntity orderEntity = ordersRepository.getOrderById(id);
            log.info("Order retrieved successfully");

            orderProductResponseModel.setId(orderEntity.getId());
            orderProductResponseModel.setProductId(orderEntity.getProductId());
            orderProductResponseModel.setQuantity(orderEntity.getQuantity());
            orderProductResponseModel.setPrice(orderEntity.getPrice());
            orderProductResponseModel.setStatus(orderEntity.getStatus());

            ProductResponseModel productDetails = new ProductResponseModel();

            try {
                log.info("Retrieving productDetails with feign client");
                productDetails = productServiceClient.getProductDetails(orderEntity.getProductId());

            } catch (Exception e) {
                log.info("Product Not Found");
                productDetails.setMessage("Couldn't find product with id =" +orderEntity.getProductId());
            }

            orderProductResponseModel.setProductDetails(productDetails);

        } catch (Exception e) {
            log.info("Couldn't find order in database");
            orderProductResponseModel.setSuccess(false);
            orderProductResponseModel.setMessage("Order Not Found");
        }

        log.info("Exiting getOrderDetailsById method");
        return orderProductResponseModel;
    }

    @Override
    public OrderProductResponseModel createNewOrder(OrderRequestModel orderRequestModel) {
        log.info("Entering createNewOrder method");

        OrderProductResponseModel orderProductResponseModel = new OrderProductResponseModel();

        try {
            log.info("Retrieving product details with feign client");
            ProductResponseModel productDetails = productServiceClient.getProductDetails(orderRequestModel.getProductId());
            log.info("Product details retrieved successfully");
            OrderEntity orderEntity = new OrderEntity();
            String id = UUID.randomUUID().toString();
            orderEntity.setId(id);
            orderEntity.setProductId(orderRequestModel.getProductId());
            orderEntity.setQuantity(orderRequestModel.getQuantity());
            BigDecimal price = productDetails.getPrice().multiply(BigDecimal.valueOf(orderRequestModel.getQuantity()));
            orderEntity.setPrice(price);
            orderEntity.setStatus("pending");
            log.info("Saving new order");
            ordersRepository.save(orderEntity);
            log.info("Order saved");
            orderProductResponseModel = new ModelMapper().map(orderEntity, OrderProductResponseModel.class);
            orderProductResponseModel.setProductDetails(productDetails);

        } catch (Exception e) {
            orderProductResponseModel.setMessage("Could not find product with id = "+orderRequestModel.getProductId());
        }
        log.info("Exiting createNewOrder method");
        return orderProductResponseModel;
    }

    public OrderResponseModel completedOrder(String id) {
        log.info("Entering completedOrder method");
        OrderEntity orderEntity = ordersRepository.getOrderById(id);
        log.info("Exiting completedOrder method");
        ProductResponseModel productDetails = productServiceClient.getProductDetails(orderEntity.getProductId());
        OrderResponseModel orderResponseModel = new OrderResponseModel();
        if ((productDetails.getStock() - orderEntity.getQuantity() >= 0) && (orderEntity.getStatus().equals("pending"))) {
            orderEntity.setStatus("completed");
            ordersRepository.save(orderEntity);
            int previousStock = productDetails.getStock();
            log.info("Entering reduceStock method");
            ProductResponseModel productResponseModel = productServiceClient.reduceStock(orderEntity.getProductId(), orderEntity.getQuantity());
            log.info("Exiting reduceStock method");
            log.info("Exiting completedOrder method");
            orderResponseModel = new ModelMapper().map(orderEntity, OrderResponseModel.class);
            orderResponseModel.setSuccess(true);
            orderResponseModel.setMessage("The product stock has been successfully updated from " + previousStock + " to " + productResponseModel.getStock() + ".");
            orderResponseModel.setSuccess(true);
        } else {
            orderResponseModel.setSuccess(false);
            if (productDetails.getStock()-orderEntity.getQuantity() < 0) {
                orderResponseModel.setMessage("Insufficient stock available to fulfill the order");
            } else  {
                orderResponseModel.setMessage("Order with id "+id+" is already completed");
            }
        }
        return orderResponseModel;

    }
}