package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.OrderDTO;
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
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDTO);
    }

    @Override
    public OrderDTO findById(String id) {
        return orderMapper.toDTO(orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @Override
    public List<OrderDTO> findByOrderDateTime(LocalDateTime orderDateTime) {
        return orderRepository.findByOrderDateTime(orderDateTime)
                .orElseThrow(() -> new OrderNotFoundException(orderDateTime))
                .stream().map(orderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> findByDeliveryDateTime(LocalDateTime deliveryDateTime) {
        return orderRepository.findByDeliveryDateTime(deliveryDateTime)
                .orElseThrow(() -> new OrderNotFoundException(deliveryDateTime))
                .stream().map(orderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> findByOrderState(OrderState orderState) {
        return orderRepository.findByOrderState(orderState)
                .orElseThrow(() -> new OrderNotFoundException(orderState.name()))
                .stream().map(orderMapper::toDTO).toList();
    }

    @Override
    public List<OrderDTO> findByClientId(String clientId) {
        return orderRepository.findByClientId(clientId)
                .orElseThrow(() -> new OrderNotFoundException(clientId))
                .stream().map(orderMapper::toDTO).toList();
    }

    @Override
    public OrderDTO update(String id, OrderDTO updatedOrderDTO) {
        Order actual = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        actual.setOrderDateTime(updatedOrderDTO.getOrderDateTime());
        actual.setDeliveryDateTime(updatedOrderDTO.getDeliveryDateTime());
        actual.setPaymentState(updatedOrderDTO.getPaymentState());
        actual.setOrderState(updatedOrderDTO.getOrderState());
        return orderMapper.toDTO(orderRepository.save(actual));
    }

    @Override
    public void deleteById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
    }
}
