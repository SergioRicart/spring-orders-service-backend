package com.rial.orderspring.model;

import com.rial.orderspring.constants.IBaseConstant;
import com.rial.orderspring.enums.ProductState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = IBaseConstant.APP_SCHEMA, name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String name;

    private String description;

    @Positive
    private double price;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

}
