package com.rial.orderspring.service;

import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.exception.ProductNotFoundException;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.repository.ProductRepository;
import com.rial.orderspring.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId("1");
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(99.99);
        product.setProductState(ProductState.ACTIVE);
    }

    @Test
    void create_ShouldReturnSavedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals(99.99, result.getPrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findAll_ShouldReturnPageOfProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = productService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        Product result = productService.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Product", result.getName());
    }

    @Test
    void findById_WhenProductNotExists_ShouldThrowException() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.findById("999");
        });
    }

    @Test
    void findByName_WhenProductExists_ShouldReturnProduct() {
        when(productRepository.findByName("Test Product")).thenReturn(Optional.of(product));

        Product result = productService.findByName("Test Product");

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }

    @Test
    void findByName_WhenProductNotExists_ShouldThrowException() {
        when(productRepository.findByName("Unknown")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.findByName("Unknown");
        });
    }

    @Test
    void findByProductState_ShouldReturnListOfProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findByProductState(ProductState.ACTIVE))
                .thenReturn(Optional.of(products));

        List<Product> result = productService.findByProductState(ProductState.ACTIVE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ProductState.ACTIVE, result.get(0).getProductState());
    }

    @Test
    void update_ShouldReturnUpdatedProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(149.99);
        updatedProduct.setProductState(ProductState.INACTIVE);

        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.update("1", updatedProduct);

        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteById_WhenProductExists_ShouldDeleteProduct() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteById("1");

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteById_WhenProductNotExists_ShouldThrowException() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteById("999");
        });
    }
}