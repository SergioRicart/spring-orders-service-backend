package com.rial.orderspring.service;

import com.rial.orderspring.dto.request.OrderRequest;
import com.rial.orderspring.dto.response.OrderResponse;
import com.rial.orderspring.enums.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    Page<OrderResponse> findAll(Pageable pageable);
    OrderResponse findById(String id);
    List<OrderResponse> findByOrderDateTime(LocalDateTime orderDateTime);
    List<OrderResponse> findByDeliveryDateTime(LocalDateTime deliveryDateTime);
    List<OrderResponse> findByOrderState(OrderState orderState);
    List<OrderResponse> findByClientId(String clientId);
    OrderResponse update(String id, OrderRequest request);
    void deleteById(String id);
}
