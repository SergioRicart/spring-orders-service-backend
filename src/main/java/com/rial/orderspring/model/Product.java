package com.rial.orderspring.model;

import com.rial.orderspring.constants.IBaseConstant;
import com.rial.orderspring.enums.ProductState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = IBaseConstant.APP_SCHEMA, name = "prducts")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    private long price;

    private ProductState productState;

}
