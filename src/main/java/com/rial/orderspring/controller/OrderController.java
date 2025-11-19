package com.rial.orderspring.controller;

import com.rial.orderspring.enums.OrderState;
import com.rial.orderspring.exception.ProductNotFoundException;
import com.rial.orderspring.model.Order;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody Order order) {

        return ResponseEntity.ok(orderService.create(order));
    }

    @GetMapping
    public ResponseEntity<Page<Order>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size ){

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){

        return ResponseEntity.ok(orderService.findById(id));
    }


    @GetMapping("/get/orderDate/{orderDateTime}")
    public ResponseEntity<?> findByOrderDateTime(@PathVariable LocalDateTime orderDateTime){
        return ResponseEntity.ok(orderService.findByOrderDateTime(orderDateTime));
    };

    @GetMapping("/get/deliveryDate/{deliveryDateTime}")
    public ResponseEntity<?> findByDeliveryDateTime(@PathVariable LocalDateTime deliveryDateTime){
        return ResponseEntity.ok(orderService.findByDeliveryDateTime(deliveryDateTime));
    };


    @GetMapping("/get/orderState/{orderState}")
    public ResponseEntity<?> findByOrderState(@PathVariable OrderState orderState){

        return ResponseEntity.ok(orderService.findByOrderState(orderState));
    };

    @GetMapping("/get/client/{clientId}")
    public ResponseEntity<?> findByClientId(@PathVariable String clientId){

        return ResponseEntity.ok(orderService.findByClientId(clientId));
    };

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> update(@PathVariable String id, @RequestBody Order updatedOrder){
        Order order = orderService.update(id, updatedOrder);

        return ResponseEntity.ok(order);
    };

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){

        orderService.deleteById(id);

        return ResponseEntity.noContent().build();
    };

}
