package com.rial.orderspring.dto.request;

import lombok.Data;

@Data
public class OrderProductRequest {
    private String productId;
    private int quantity;
}
