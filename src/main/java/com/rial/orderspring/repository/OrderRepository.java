package com.rial.orderspring.repository;

import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<List<Order>> findByOrderDateTime(LocalDateTime orderDateTime);

    Optional<List<Order>> findByDeliveryDateTime(LocalDateTime deliveryDateTime);

    Optional<List<Order>> findByOrderState(OrderState orderState);

    Optional<List<Order>> findByClientId(String ClientId);

}
