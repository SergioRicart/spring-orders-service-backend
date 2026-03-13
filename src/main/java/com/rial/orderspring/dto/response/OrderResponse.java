package com.rial.orderspring.dto.response;

import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.enums.PaymentState;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private LocalDateTime orderDateTime;
    private LocalDateTime deliveryDateTime;
    private PaymentState paymentState;
    private OrderState orderState;
    private String clientId;
    private List<OrderProductResponse> orderProducts;
}
