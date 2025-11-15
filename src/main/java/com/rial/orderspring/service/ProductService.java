package com.rial.orderspring.service;

import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;

import java.util.List;

public interface ProductService {

    Product create(Product product);

    List<Product> findAll();

    List<Product> findByProductState(ProductState productState);

    Product findById(String id);

    Product findByName(String name);

    Product update(String id, Product updatedProduct);

    void deleteById(String id);


}
