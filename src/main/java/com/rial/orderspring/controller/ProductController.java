package com.rial.orderspring.controller;

import com.rial.orderspring.exception.ProductNotFoundException;
import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController{


    @Autowired
    ProductServiceImpl productServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product) {

        return ResponseEntity.ok(productServiceImpl.save(product));
    }

    /* AL CONSULTAR LA RUTA POR DEFECTO MUESTRA TODOS LOS PRODUCTOS */
    @GetMapping
    public ResponseEntity<List<Product>> findAll(){

        return ResponseEntity.ok(productServiceImpl.findAll());
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) throws ProductNotFoundException {

        return ResponseEntity.ok(productServiceImpl.findById(id));
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) throws ProductNotFoundException {

        return ResponseEntity.ok(productServiceImpl.findByName(name));
    }

    @GetMapping("/find/state/{state}")
    public ResponseEntity<?> findByProductState(@PathVariable ProductState state) throws ProductNotFoundException {

        return ResponseEntity.ok(productServiceImpl.findByProductState(state));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product updatedProduct) throws ProductNotFoundException {

        Product product = productServiceImpl.update(id, updatedProduct);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(String id) throws ProductNotFoundException {

        productServiceImpl.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
