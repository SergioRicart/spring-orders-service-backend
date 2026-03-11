package com.rial.orderspring.dto;

import com.rial.orderspring.enums.ProductState;
import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private double price;
    private ProductState productState;
}
