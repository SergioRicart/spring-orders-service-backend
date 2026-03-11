package com.rial.orderspring.dto;

import lombok.Data;

@Data
public class OrderProductResponse {
    private String id;
    private String productId;
    private int quantity;
}
