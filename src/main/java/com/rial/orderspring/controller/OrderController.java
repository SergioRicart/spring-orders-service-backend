package com.rial.orderspring.controller;

import com.rial.orderspring.dto.request.OrderRequest;
import com.rial.orderspring.dto.response.OrderResponse;
import com.rial.orderspring.enums.OrderState;
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

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/get/orderDate/{orderDateTime}")
    public ResponseEntity<List<OrderResponse>> findByOrderDateTime(@PathVariable LocalDateTime orderDateTime) {
        return ResponseEntity.ok(orderService.findByOrderDateTime(orderDateTime));
    }

    @GetMapping("/get/deliveryDate/{deliveryDateTime}")
    public ResponseEntity<List<OrderResponse>> findByDeliveryDateTime(@PathVariable LocalDateTime deliveryDateTime) {
        return ResponseEntity.ok(orderService.findByDeliveryDateTime(deliveryDateTime));
    }

    @GetMapping("/get/orderState/{orderState}")
    public ResponseEntity<List<OrderResponse>> findByOrderState(@PathVariable OrderState orderState) {
        return ResponseEntity.ok(orderService.findByOrderState(orderState));
    }

    @GetMapping("/get/client/{clientId}")
    public ResponseEntity<List<OrderResponse>> findByClientId(@PathVariable String clientId) {
        return ResponseEntity.ok(orderService.findByClientId(clientId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable String id, @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
