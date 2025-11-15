package com.rial.orderspring.controller;

import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController{


    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product product) {

        return ResponseEntity.ok(productService.create(product));
    }

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size ){

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){

        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/get/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {

        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/get/state/{state}")
    public ResponseEntity<?> findByProductState(@PathVariable ProductState state) {

        return ResponseEntity.ok(productService.findByProductState(state));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product updatedProduct) {

        Product product = productService.update(id, updatedProduct);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {

        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
