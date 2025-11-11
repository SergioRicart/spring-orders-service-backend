package com.rial.orderspring.controller;

import com.rial.orderspring.model.Product;
import com.rial.orderspring.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    /* AL CONSULTAR LA RUTA POR DEFECTO MUESTRA TODOS LOS PRODUCTOS */
    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(productServiceImpl.findAll());
    }





}
