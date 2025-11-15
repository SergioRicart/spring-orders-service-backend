package com.rial.orderspring.service;

import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product create(Product product);

    Page<Product> findAll(Pageable pageable);

    List<Product> findByProductState(ProductState productState);

    Product findById(String id);

    Product findByName(String name);

    Product update(String id, Product updatedProduct);

    void deleteById(String id);


}
