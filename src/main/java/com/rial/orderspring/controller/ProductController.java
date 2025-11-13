package com.rial.orderspring.controller;

import com.rial.orderspring.exception.ProductNotFoundException;
import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController{


    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product) {

        return ResponseEntity.ok(productService.save(product));
    }

    /* AL CONSULTAR LA RUTA POR DEFECTO MUESTRA TODOS LOS PRODUCTOS */
    @GetMapping
    public ResponseEntity<List<Product>> findAll(){

        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) throws ProductNotFoundException {

        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) throws ProductNotFoundException {

        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/find/state/{state}")
    public ResponseEntity<?> findByProductState(@PathVariable ProductState state) throws ProductNotFoundException {

        return ResponseEntity.ok(productService.findByProductState(state));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product updatedProduct) throws ProductNotFoundException {

        Product product = productService.update(id, updatedProduct);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) throws ProductNotFoundException {

        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
