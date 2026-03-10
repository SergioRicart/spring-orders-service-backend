package com.rial.orderspring.service;

import com.rial.orderspring.dto.OrderDTO;
import com.rial.orderspring.enums.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
    Page<OrderDTO> findAll(Pageable pageable);
    OrderDTO findById(String id);
    List<OrderDTO> findByOrderDateTime(LocalDateTime orderDateTime);
    List<OrderDTO> findByDeliveryDateTime(LocalDateTime deliveryDateTime);
    List<OrderDTO> findByOrderState(OrderState orderState);
    List<OrderDTO> findByClientId(String clientId);
    OrderDTO update(String id, OrderDTO updatedOrderDTO);
    void deleteById(String id);
}
