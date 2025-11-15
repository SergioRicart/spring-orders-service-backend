package com.rial.orderspring.service.impl;

import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.exception.OrderNotFoundException;
import com.rial.orderspring.model.Order;
import com.rial.orderspring.repository.OrderRepository;
import com.rial.orderspring.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> findByOrderDateTime(LocalDateTime orderDateTime) {
        return orderRepository.findByOrderDateTime(orderDateTime).orElseThrow(() -> new OrderNotFoundException(orderDateTime));
    }

    @Override
    public List<Order> findByDeliveryDateTime(LocalDateTime deliveryDateTime) {
        return orderRepository.findByDeliveryDateTime(deliveryDateTime).orElseThrow(() -> new OrderNotFoundException(deliveryDateTime));
    }

    @Override
    public List<Order> findByOrderState(OrderState orderState) {
        return orderRepository.findByOrderState(orderState).orElseThrow(() -> new OrderNotFoundException(orderState.name()));
    }

    @Override
    public List<Order> findByClientId(String clientId) {
        return null;
    }

    @Override
    public Order update(String id, Order updatedOrder) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
