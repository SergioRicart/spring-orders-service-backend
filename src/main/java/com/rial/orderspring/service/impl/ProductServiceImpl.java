package com.rial.orderspring.service.impl;

import com.rial.orderspring.exception.ProductNotFoundException;
import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.repository.ProductRepository;
import com.rial.orderspring.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name));
    }

    @Override
    public List<Product> findByProductState(ProductState state) {
        return productRepository.findByProductState(state)
                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(state)));
    }

    @Override
    public Product update(String id, Product updatedProduct) {

        Product actualProduct = findById(id);

        BeanUtils.copyProperties(updatedProduct, actualProduct, "id");

        return productRepository.save(actualProduct);
    }

    @Override
    public void deleteById(String id) {

        Product product = findById(id);

        productRepository.delete(product);

    }
}
