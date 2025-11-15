package com.rial.orderspring.service;

import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {


    Order create(Order order);

    Page<Order> findAll(Pageable pageable);

    Order findById(String id);

    List<Order> findByOrderDateTime(LocalDateTime orderDateTime);

    List<Order> findByDeliveryDateTime(LocalDateTime deliveryDateTime);

    List<Order> findByOrderState(OrderState orderState);

    List<Order> findByClientId(String clientId);
    
    Order update(String id, Order updatedOrder);

    void deleteById(String id);
}
