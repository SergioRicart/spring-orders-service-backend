package com.rial.orderspring.service.impl;

import com.rial.orderspring.dto.ProductDTO;
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
    public ProductDTO create(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDTO);
    }

    @Override
    public ProductDTO findById(String id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    @Override
    public ProductDTO findByName(String name) {
        return productMapper.toDTO(productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name)));
    }

    @Override
    public List<ProductDTO> findByProductState(ProductState state) {
        return productRepository.findByProductState(state)
                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(state)))
                .stream().map(productMapper::toDTO).toList();
    }

    @Override
    public ProductDTO update(String id, ProductDTO updatedProductDTO) {
        Product actual = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        actual.setName(updatedProductDTO.getName());
        actual.setDescription(updatedProductDTO.getDescription());
        actual.setPrice(updatedProductDTO.getPrice());
        actual.setProductState(updatedProductDTO.getProductState());
        return productMapper.toDTO(productRepository.save(actual));
    }

    @Override
    public void deleteById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
