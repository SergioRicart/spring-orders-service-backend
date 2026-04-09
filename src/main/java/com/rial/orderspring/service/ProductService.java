package com.rial.orderspring.service;

import com.rial.orderspring.dto.request.ProductRequest;
import com.rial.orderspring.dto.response.ProductResponse;
import com.rial.orderspring.enums.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    Page<ProductResponse> findAll(Pageable pageable);
    List<ProductResponse> findByProductState(ProductState productState);
    ProductResponse findById(String id);
    ProductResponse findByName(String name);
    ProductResponse update(String id, ProductRequest request);
    void deleteById(String id);
}
