package com.rial.orderspring.controller;

import com.rial.orderspring.dto.OrderDTO;
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
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.create(orderDTO));
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/get/orderDate/{orderDateTime}")
    public ResponseEntity<List<OrderDTO>> findByOrderDateTime(@PathVariable LocalDateTime orderDateTime) {
        return ResponseEntity.ok(orderService.findByOrderDateTime(orderDateTime));
    }

    @GetMapping("/get/deliveryDate/{deliveryDateTime}")
    public ResponseEntity<List<OrderDTO>> findByDeliveryDateTime(@PathVariable LocalDateTime deliveryDateTime) {
        return ResponseEntity.ok(orderService.findByDeliveryDateTime(deliveryDateTime));
    }

    @GetMapping("/get/orderState/{orderState}")
    public ResponseEntity<List<OrderDTO>> findByOrderState(@PathVariable OrderState orderState) {
        return ResponseEntity.ok(orderService.findByOrderState(orderState));
    }

    @GetMapping("/get/client/{clientId}")
    public ResponseEntity<List<OrderDTO>> findByClientId(@PathVariable String clientId) {
        return ResponseEntity.ok(orderService.findByClientId(clientId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable String id, @RequestBody OrderDTO updatedOrderDTO) {
        return ResponseEntity.ok(orderService.update(id, updatedOrderDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
