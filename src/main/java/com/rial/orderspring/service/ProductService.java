package com.rial.orderspring.service;

import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    List<Product> findByProductState(ProductState productState);

    Product findById(String id);

    Product findByName(String name);

    Product update(String id, Product updatedProduct) throws Exception;

    void deleteById(String id) throws Exception;


}
