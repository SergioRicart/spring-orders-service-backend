package com.rial.orderspring.service;

import com.rial.orderspring.dto.request.ProductRequest;
import com.rial.orderspring.dto.response.ProductResponse;
import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.exception.ProductNotFoundException;
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

    private ProductResponse productResponse;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setDescription("Test Description");
        productRequest.setPrice(99.99);
        productRequest.setProductState(ProductState.ACTIVE);

        productResponse = new ProductResponse();
        productResponse.setId("1");
        productResponse.setName("Test Product");
        productResponse.setDescription("Test Description");
        productResponse.setPrice(99.99);
        productResponse.setProductState(ProductState.ACTIVE);
    }

    @Test
    void create_ShouldReturnSavedProduct() {
        when(productRepository.save(any(ProductRequest.class))).thenReturn(productResponse);

        ProductResponse result = productService.create(productRequest);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals(99.99, result.getPrice());
        verify(productRepository, times(1)).save(any(ProductRequest.class));
    }

    @Test
    void findAll_ShouldReturnPageOfProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductResponse> page = new PageImpl<>(Arrays.asList(productResponse));
        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<ProductResponse> result = productService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        when(productRepository.findById("1")).thenReturn(Optional.of(productResponse));

        ProductResponse result = productService.findById("1");

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
        when(productRepository.findByName("Test Product")).thenReturn(Optional.of(productResponse));

        ProductResponse result = productService.findByName("Test Product");

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
        List<ProductResponse> products = Arrays.asList(productResponse);
        when(productRepository.findByProductState(ProductState.ACTIVE))
                .thenReturn(Optional.of(products));

        List<ProductResponse> result = productService.findByProductState(ProductState.ACTIVE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ProductState.ACTIVE, result.get(0).getProductState());
    }

    @Test
    void update_ShouldReturnUpdatedProduct() {
        ProductRequest updatedRequest = new ProductRequest();
        updatedRequest.setName("Updated Product");
        updatedRequest.setPrice(149.99);
        updatedRequest.setProductState(ProductState.INACTIVE);

        when(productRepository.findById("1")).thenReturn(Optional.of(productResponse));
        when(productRepository.save(any(ProductRequest.class))).thenReturn(productResponse);

        ProductResponse result = productService.update("1", updatedRequest);

        assertNotNull(result);
        verify(productRepository, times(1)).save(any(ProductRequest.class));
    }

    @Test
    void deleteById_WhenProductExists_ShouldDeleteProduct() {
        when(productRepository.findById("1")).thenReturn(Optional.of(productResponse));
        doNothing().when(productRepository).delete(any(ProductResponse.class));

        productService.deleteById("1");

        verify(productRepository, times(1)).delete(any(ProductResponse.class));
    }

    @Test
    void deleteById_WhenProductNotExists_ShouldThrowException() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteById("999");
        });
    }
}