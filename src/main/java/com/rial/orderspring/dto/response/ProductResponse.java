package com.rial.orderspring.dto.response;

import com.rial.orderspring.enums.ProductState;
import lombok.Data;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private double price;
    private ProductState productState;
}
