package com.rial.orderspring.model;

import com.rial.orderspring.constants.IBaseConstant;
import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.enums.PaymentState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter @Entity
@Table(schema = IBaseConstant.APP_SCHEMA, name = "orders")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime  orderDateTime;

    private LocalDateTime deliveryDateTime;

    @Enumerated(EnumType.STRING)
    private PaymentState paymentState;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

}
