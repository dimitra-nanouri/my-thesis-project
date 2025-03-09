package com.modulith.app.orders;

import com.modulith.app.products.ProductApi;
import com.modulith.app.shared.ProductResponseModel;
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
class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    private final ProductApi productApi;

    @Override
    public List<OrderResponseModel> getAllOrders() {
        log.info("Entering getAllOrders method");
        log.info("Retrieving orders from database");
        Iterable<OrderEntity> entities = ordersRepository.findAll();
        List<OrderResponseModel> orders = new ArrayList<>();
        for (OrderEntity entity : entities) {
            orders.add(new ModelMapper().map(entity, OrderResponseModel.class));
        }
        return orders;
    }

    @Override
    public OrderProductResponseModel getOrderDetailsById(String id) {
        OrderProductResponseModel orderProductResponseModel = new OrderProductResponseModel();

        try {
            log.info("Entering getOrderDetailsById method with id={}", id);
            log.info("Retrieving order from database");
            OrderEntity orderEntity = ordersRepository.getOrderById(id);
            log.info("Order retrieved successfully");

            orderProductResponseModel.setId(orderEntity.getId());
            orderProductResponseModel.setProductId(orderEntity.getProductId());
            orderProductResponseModel.setQuantity(orderEntity.getQuantity());
            orderProductResponseModel.setStatus(orderEntity.getStatus());
            orderProductResponseModel.setPrice(orderEntity.getPrice());

            ProductResponseModel product = new ProductResponseModel();

            try {
                product = productApi.getProductById(orderEntity.getProductId());
            } catch (Exception e) {
                log.info("Product Not Found");
                product.setMessage("Could not find product with id = "+orderEntity.getProductId());
            }

            orderProductResponseModel.setProductResponseModel(product);

        } catch (Exception e) {
            log.error("Order Not Found");
            orderProductResponseModel.setSuccess(false);
            orderProductResponseModel.setMessage("Order Not Found");
        }
        log.info("Exiting getOrderDetailsById method");
        return orderProductResponseModel;
    }

    @Override
    public OrderProductResponseModel createNewOrder(OrderRequestModel orderDetails) {
        OrderProductResponseModel orderProductResponseModel = new OrderProductResponseModel();

        try {
            log.info("Entering createNewOrder method");
            log.info("Retrieving product details through api");
            ProductResponseModel productResponseModel = productApi.getProductById(orderDetails.getProductId());
            log.info("Product details retrieved successfully");

            OrderEntity orderEntity = new OrderEntity();
            String id = UUID.randomUUID().toString();
            orderEntity.setId(id);
            orderEntity.setProductId(orderDetails.getProductId());
            orderEntity.setQuantity(orderDetails.getQuantity());
            BigDecimal price = productResponseModel.getPrice().multiply(BigDecimal.valueOf(orderDetails.getQuantity()));
            orderEntity.setPrice(price);
            orderEntity.setStatus("pending");
            log.info("Saving new order");
            ordersRepository.save(orderEntity);
            log.info("Order saved");
            orderProductResponseModel.setId(orderEntity.getId());
            orderProductResponseModel.setProductId(orderEntity.getProductId());
            orderProductResponseModel.setQuantity(orderEntity.getQuantity());
            orderProductResponseModel.setPrice(orderEntity.getPrice());
            orderProductResponseModel.setStatus(orderEntity.getStatus());
            orderProductResponseModel.setProductResponseModel(productResponseModel);
            log.info("Exiting createNewOrder method");

        } catch (Exception e) {
            log.error("Product Not found");
            orderProductResponseModel.setMessage("Could not find product with id =  "+orderDetails.getProductId());
            orderProductResponseModel.setSuccess(false);
        }
        return orderProductResponseModel;
    }

    @Override
    public OrderResponseModel completedOrder(String id) {
        log.info("Entering completedOrder method");
        OrderEntity orderEntity = ordersRepository.getOrderById(id);
        OrderResponseModel orderResponseModel = new OrderResponseModel();

        log.info("Retrieving product details through api");
        ProductResponseModel product = productApi.getProductById(orderEntity.getProductId());
        log.info("Product details retrieved successfully");

        if (product.getId() != null) {
            if ((product.getStock() - orderEntity.getQuantity() >= 0) && (orderEntity.getStatus().equals("pending"))) {
                orderEntity.setStatus("completed");
                ordersRepository.save(orderEntity);
                int previousStock = product.getStock();
                log.info("Entering reduceStock method");
                ProductResponseModel productResponseModel = productApi.reduceStock(orderEntity.getProductId(), orderEntity.getQuantity());
                log.info("Exiting reduceStock method");
                log.info("Exiting completedOrder method");
                orderResponseModel = new ModelMapper().map(orderEntity, OrderResponseModel.class);
                orderResponseModel.setSuccess(true);
                orderResponseModel.setMessage("The product stock has been successfully updated from " + previousStock + " to " + productResponseModel.getStock() + ".");
                orderResponseModel.setSuccess(true);
            } else {
                orderResponseModel.setSuccess(false);
                if (product.getStock() - orderEntity.getQuantity() < 0) {
                    orderResponseModel.setMessage("Insufficient stock available to fulfill the order");
                } else {
                    orderResponseModel.setMessage("Order with id " + id + " is already completed");
                }
            }
        } else {
            orderResponseModel.setMessage("Could not find product with id = " +id);
        }
        return orderResponseModel;
    }

}
