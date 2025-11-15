package com.rial.orderspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.exception.ProductNotFoundException;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

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
    void create_ShouldReturnCreatedProduct() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(99.99));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    void findAll_ShouldReturnPageOfProducts() throws Exception {
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productService.findAll(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].name").value("Test Product"));

        verify(productService, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() throws Exception {
        when(productService.findById("1")).thenReturn(product);

        mockMvc.perform(get("/api/products/get/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).findById("1");
    }

    @Test
    void findById_WhenProductNotExists_ShouldReturnError() throws Exception {
        when(productService.findById("999")).thenThrow(new ProductNotFoundException("999"));

        mockMvc.perform(get("/api/products/get/id/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product 999 not found"));
    }

    @Test
    void findByName_ShouldReturnProduct() throws Exception {
        when(productService.findByName("Test Product")).thenReturn(product);

        mockMvc.perform(get("/api/products/get/name/Test Product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).findByName("Test Product");
    }

    @Test
    void findByProductState_ShouldReturnListOfProducts() throws Exception {
        List<Product> products = Arrays.asList(product);
        when(productService.findByProductState(ProductState.ACTIVE)).thenReturn(products);

        mockMvc.perform(get("/api/products/get/state/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productState").value("ACTIVE"));

        verify(productService, times(1)).findByProductState(ProductState.ACTIVE);
    }

    @Test
    void update_ShouldReturnUpdatedProduct() throws Exception {
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(149.99);

        when(productService.update(eq("1"), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk());

        verify(productService, times(1)).update(eq("1"), any(Product.class));
    }

    @Test
    void deleteById_ShouldReturnNoContent() throws Exception {
        doNothing().when(productService).deleteById("1");

        mockMvc.perform(delete("/api/products/delete/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteById("1");
    }
}