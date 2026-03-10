package com.rial.orderspring.dto;

import lombok.Data;

@Data
public class OrderProductDTO {
    private String id;
    private String productId;
    private int quantity;
}
