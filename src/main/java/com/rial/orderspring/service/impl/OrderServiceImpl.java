package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.OrderRequest;
import com.rial.orderspring.dto.OrderResponse;
import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.exception.OrderNotFoundException;
import com.rial.orderspring.mapper.OrderMapper;
import com.rial.orderspring.model.Order;
import com.rial.orderspring.repository.OrderRepository;
import com.rial.orderspring.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        return orderMapper.toResponse(orderRepository.save(orderMapper.toEntity(request)));
    }

    @Override
    public Page<OrderResponse> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toResponse);
    }

    @Override
    public OrderResponse findById(String id) {
        return orderMapper.toResponse(orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @Override
    public List<OrderResponse> findByOrderDateTime(LocalDateTime orderDateTime) {
        return orderRepository.findByOrderDateTime(orderDateTime)
                .orElseThrow(() -> new OrderNotFoundException(orderDateTime))
                .stream().map(orderMapper::toResponse).toList();
    }

    @Override
    public List<OrderResponse> findByDeliveryDateTime(LocalDateTime deliveryDateTime) {
        return orderRepository.findByDeliveryDateTime(deliveryDateTime)
                .orElseThrow(() -> new OrderNotFoundException(deliveryDateTime))
                .stream().map(orderMapper::toResponse).toList();
    }

    @Override
    public List<OrderResponse> findByOrderState(OrderState orderState) {
        return orderRepository.findByOrderState(orderState)
                .orElseThrow(() -> new OrderNotFoundException(orderState.name()))
                .stream().map(orderMapper::toResponse).toList();
    }

    @Override
    public List<OrderResponse> findByClientId(String clientId) {
        return orderRepository.findByClientId(clientId)
                .orElseThrow(() -> new OrderNotFoundException(clientId))
                .stream().map(orderMapper::toResponse).toList();
    }

    @Override
    public OrderResponse update(String id, OrderRequest request) {
        Order actual = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        actual.setOrderDateTime(request.getOrderDateTime());
        actual.setDeliveryDateTime(request.getDeliveryDateTime());
        actual.setPaymentState(request.getPaymentState());
        actual.setOrderState(request.getOrderState());
        return orderMapper.toResponse(orderRepository.save(actual));
    }

    @Override
    public void deleteById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
    }
}
