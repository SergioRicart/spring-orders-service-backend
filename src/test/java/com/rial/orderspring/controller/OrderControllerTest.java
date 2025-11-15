package com.rial.orderspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.enums.PaymentState;
import com.rial.orderspring.exception.OrderNotFoundException;
import com.rial.orderspring.model.Client;
import com.rial.orderspring.model.Order;
import com.rial.orderspring.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    private Order order;
    private Client client;
    private LocalDateTime orderDateTime;
    private LocalDateTime deliveryDateTime;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("client-1");
        client.setName("Test Client");

        orderDateTime = LocalDateTime.of(2024, 1, 1, 10, 0);
        deliveryDateTime = LocalDateTime.of(2024, 1, 4, 10, 0);

        order = new Order();
        order.setId("order-1");
        order.setOrderDateTime(orderDateTime);
        order.setDeliveryDateTime(deliveryDateTime);
        order.setPaymentState(PaymentState.PAID);
        order.setOrderState(OrderState.ORDERED);
        order.setClient(client);
    }

    @Test
    void create_ShouldReturnCreatedOrder() throws Exception {
        when(orderService.create(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("order-1"))
                .andExpect(jsonPath("$.orderState").value("ORDERED"))
                .andExpect(jsonPath("$.paymentState").value("PAID"));

        verify(orderService, times(1)).create(any(Order.class));
    }

    @Test
    void findAll_ShouldReturnPageOfOrders() throws Exception {
        Page<Order> page = new PageImpl<>(Arrays.asList(order));
        when(orderService.findAll(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/orders")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("order-1"))
                .andExpect(jsonPath("$.content[0].orderState").value("ORDERED"));

        verify(orderService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void findById_WhenOrderExists_ShouldReturnOrder() throws Exception {
        when(orderService.findById("order-1")).thenReturn(order);

        mockMvc.perform(get("/api/orders/get/id/order-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("order-1"))
                .andExpect(jsonPath("$.orderState").value("ORDERED"));

        verify(orderService, times(1)).findById("order-1");
    }

    @Test
    void findById_WhenOrderNotExists_ShouldReturnError() throws Exception {
        when(orderService.findById("999")).thenThrow(new OrderNotFoundException("999"));

        mockMvc.perform(get("/api/orders/get/id/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Order 999 not found"));
    }

    @Test
    void findByOrderState_ShouldReturnListOfOrders() throws Exception {
        List<Order> orders = Arrays.asList(order);
        when(orderService.findByOrderState(OrderState.ORDERED)).thenReturn(orders);

        mockMvc.perform(get("/api/orders/get/orderState/ORDERED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderState").value("ORDERED"));

        verify(orderService, times(1)).findByOrderState(OrderState.ORDERED);
    }

    @Test
    void findByOrderDateTime_ShouldReturnListOfOrders() throws Exception {
        List<Order> orders = Arrays.asList(order);
        when(orderService.findByOrderDateTime(any(LocalDateTime.class))).thenReturn(orders);

        mockMvc.perform(get("/api/orders/get/orderDate/2024-01-01T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("order-1"));

        verify(orderService, times(1)).findByOrderDateTime(any(LocalDateTime.class));
    }

    @Test
    void findByDeliveryDateTime_ShouldReturnListOfOrders() throws Exception {
        List<Order> orders = Arrays.asList(order);
        when(orderService.findByDeliveryDateTime(any(LocalDateTime.class))).thenReturn(orders);

        mockMvc.perform(get("/api/orders/get/deliveryDate/2024-01-04T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("order-1"));

        verify(orderService, times(1)).findByDeliveryDateTime(any(LocalDateTime.class));
    }
}