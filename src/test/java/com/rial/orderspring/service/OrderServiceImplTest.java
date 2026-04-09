package com.rial.orderspring.service;

import com.rial.orderspring.dto.request.OrderRequest;
import com.rial.orderspring.dto.response.OrderResponse;
import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.enums.PaymentState;
import com.rial.orderspring.exception.OrderNotFoundException;
import com.rial.orderspring.repository.OrderRepository;
import com.rial.orderspring.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderResponse orderResponse;
    private OrderRequest orderRequest;
    private LocalDateTime orderDateTime;
    private LocalDateTime deliveryDateTime;

    @BeforeEach
    void setUp() {
        orderDateTime = LocalDateTime.now();
        deliveryDateTime = LocalDateTime.now().plusDays(3);

        orderRequest = new OrderRequest();
        orderRequest.setOrderDateTime(orderDateTime);
        orderRequest.setDeliveryDateTime(deliveryDateTime);
        orderRequest.setPaymentState(PaymentState.PAID);
        orderRequest.setOrderState(OrderState.ORDERED);
        orderRequest.setClientId("client-1");

        orderResponse = new OrderResponse();
        orderResponse.setId("order-1");
        orderResponse.setOrderDateTime(orderDateTime);
        orderResponse.setDeliveryDateTime(deliveryDateTime);
        orderResponse.setPaymentState(PaymentState.PAID);
        orderResponse.setOrderState(OrderState.ORDERED);
        orderResponse.setClientId("client-1");
    }

    @Test
    void create_ShouldReturnSavedOrder() {
        when(orderRepository.save(any(OrderRequest.class))).thenReturn(orderResponse);

        OrderResponse result = orderService.create(orderRequest);

        assertNotNull(result);
        assertEquals(OrderState.ORDERED, result.getOrderState());
        assertEquals(PaymentState.PAID, result.getPaymentState());
        verify(orderRepository, times(1)).save(any(OrderRequest.class));
    }

    @Test
    void findAll_ShouldReturnPageOfOrders() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderResponse> page = new PageImpl<>(Arrays.asList(orderResponse));
        when(orderRepository.findAll(pageable)).thenReturn(page);

        Page<OrderResponse> result = orderService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(orderRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_WhenOrderExists_ShouldReturnOrder() {
        when(orderRepository.findById("order-1")).thenReturn(Optional.of(orderResponse));

        OrderResponse result = orderService.findById("order-1");

        assertNotNull(result);
        assertEquals("order-1", result.getId());
        verify(orderRepository, times(1)).findById("order-1");
    }

    @Test
    void findById_WhenOrderNotExists_ShouldThrowException() {
        when(orderRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.findById("999");
        });
    }

    @Test
    void findByOrderDateTime_ShouldReturnListOfOrders() {
        List<OrderResponse> orders = Arrays.asList(orderResponse);
        when(orderRepository.findByOrderDateTime(orderDateTime))
                .thenReturn(Optional.of(orders));

        List<OrderResponse> result = orderService.findByOrderDateTime(orderDateTime);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderDateTime, result.get(0).getOrderDateTime());
    }

    @Test
    void findByDeliveryDateTime_ShouldReturnListOfOrders() {
        List<OrderResponse> orders = Arrays.asList(orderResponse);
        when(orderRepository.findByDeliveryDateTime(deliveryDateTime))
                .thenReturn(Optional.of(orders));

        List<OrderResponse> result = orderService.findByDeliveryDateTime(deliveryDateTime);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByOrderState_ShouldReturnListOfOrders() {
        List<OrderResponse> orders = Arrays.asList(orderResponse);
        when(orderRepository.findByOrderState(OrderState.ORDERED))
                .thenReturn(Optional.of(orders));

        List<OrderResponse> result = orderService.findByOrderState(OrderState.ORDERED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(OrderState.ORDERED, result.get(0).getOrderState());
    }

    @Test
    void findByClientId_ShouldReturnListOfOrders() {
        List<OrderResponse> orders = Arrays.asList(orderResponse);
        when(orderRepository.findByClientId("client-1"))
                .thenReturn(Optional.of(orders));

        List<OrderResponse> result = orderService.findByClientId("client-1");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void update_ShouldReturnUpdatedOrder() {
        OrderRequest updatedRequest = new OrderRequest();
        updatedRequest.setOrderState(OrderState.IN_PROGRESS);
        updatedRequest.setPaymentState(PaymentState.PAID);

        when(orderRepository.findById("order-1")).thenReturn(Optional.of(orderResponse));
        when(orderRepository.save(any(OrderRequest.class))).thenReturn(orderResponse);

        OrderResponse result = orderService.update("order-1", updatedRequest);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(OrderRequest.class));
    }

    @Test
    void deleteById_WhenOrderExists_ShouldDeleteOrder() {
        when(orderRepository.findById("order-1")).thenReturn(Optional.of(orderResponse));
        doNothing().when(orderRepository).delete(any(OrderResponse.class));

        orderService.deleteById("order-1");

        verify(orderRepository, times(1)).delete(any(OrderResponse.class));
    }

    @Test
    void deleteById_WhenOrderNotExists_ShouldThrowException() {
        when(orderRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.deleteById("999");
        });
    }
}