package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.ProductRequest;
import com.rial.orderspring.dto.ProductResponse;
import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.exception.ProductNotFoundException;
import com.rial.orderspring.mapper.ProductMapper;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.repository.ProductRepository;
import com.rial.orderspring.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        return productMapper.toResponse(productRepository.save(productMapper.toEntity(request)));
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    @Override
    public ProductResponse findById(String id) {
        return productMapper.toResponse(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    @Override
    public ProductResponse findByName(String name) {
        return productMapper.toResponse(productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name)));
    }

    @Override
    public List<ProductResponse> findByProductState(ProductState state) {
        return productRepository.findByProductState(state)
                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(state)))
                .stream().map(productMapper::toResponse).toList();
    }

    @Override
    public ProductResponse update(String id, ProductRequest request) {
        Product actual = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        actual.setName(request.getName());
        actual.setDescription(request.getDescription());
        actual.setPrice(request.getPrice());
        actual.setProductState(request.getProductState());
        return productMapper.toResponse(productRepository.save(actual));
    }

    @Override
    public void deleteById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
