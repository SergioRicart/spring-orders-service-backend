package com.rial.orderspring.service;

import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.enums.PaymentState;
import com.rial.orderspring.exception.OrderNotFoundException;
import com.rial.orderspring.model.Client;
import com.rial.orderspring.model.Order;
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

    private Order order;
    private Client client;
    private LocalDateTime orderDateTime;
    private LocalDateTime deliveryDateTime;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("client-1");
        client.setName("Test Client");

        orderDateTime = LocalDateTime.now();
        deliveryDateTime = LocalDateTime.now().plusDays(3);

        order = new Order();
        order.setId("order-1");
        order.setOrderDateTime(orderDateTime);
        order.setDeliveryDateTime(deliveryDateTime);
        order.setPaymentState(PaymentState.PAID);
        order.setOrderState(OrderState.ORDERED);
        order.setClient(client);
    }

    @Test
    void create_ShouldReturnSavedOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.create(order);

        assertNotNull(result);
        assertEquals(OrderState.ORDERED, result.getOrderState());
        assertEquals(PaymentState.PAID, result.getPaymentState());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void findAll_ShouldReturnPageOfOrders() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(Arrays.asList(order));
        when(orderRepository.findAll(pageable)).thenReturn(page);

        Page<Order> result = orderService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(orderRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_WhenOrderExists_ShouldReturnOrder() {
        when(orderRepository.findById("order-1")).thenReturn(Optional.of(order));

        Order result = orderService.findById("order-1");

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
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findByOrderDateTime(orderDateTime))
                .thenReturn(Optional.of(orders));

        List<Order> result = orderService.findByOrderDateTime(orderDateTime);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderDateTime, result.get(0).getOrderDateTime());
    }

    @Test
    void findByDeliveryDateTime_ShouldReturnListOfOrders() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findByDeliveryDateTime(deliveryDateTime))
                .thenReturn(Optional.of(orders));

        List<Order> result = orderService.findByDeliveryDateTime(deliveryDateTime);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByOrderState_ShouldReturnListOfOrders() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findByOrderState(OrderState.ORDERED))
                .thenReturn(Optional.of(orders));

        List<Order> result = orderService.findByOrderState(OrderState.ORDERED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(OrderState.ORDERED, result.get(0).getOrderState());
    }

    @Test
    void findByClientId_ShouldReturnListOfOrders() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findByClientId("client-1"))
                .thenReturn(Optional.of(orders));

        List<Order> result = orderService.findByClientId("client-1");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void update_ShouldReturnUpdatedOrder() {
        Order updatedOrder = new Order();
        updatedOrder.setOrderState(OrderState.IN_PROGRESS);
        updatedOrder.setPaymentState(PaymentState.PAID);

        when(orderRepository.findById("order-1")).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.update("order-1", updatedOrder);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void deleteById_WhenOrderExists_ShouldDeleteOrder() {
        when(orderRepository.findById("order-1")).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(order);

        orderService.deleteById("order-1");

        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void deleteById_WhenOrderNotExists_ShouldThrowException() {
        when(orderRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.deleteById("999");
        });
    }
}