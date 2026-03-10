package com.rial.orderspring.service;

import com.rial.orderspring.dto.ProductDTO;
import com.rial.orderspring.enums.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);
    Page<ProductDTO> findAll(Pageable pageable);
    List<ProductDTO> findByProductState(ProductState productState);
    ProductDTO findById(String id);
    ProductDTO findByName(String name);
    ProductDTO update(String id, ProductDTO updatedProductDTO);
    void deleteById(String id);
}
