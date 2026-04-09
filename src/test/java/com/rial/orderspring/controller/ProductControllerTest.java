package com.rial.orderspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rial.orderspring.dto.request.ProductRequest;
import com.rial.orderspring.dto.response.ProductResponse;
import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.exception.ProductNotFoundException;
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
    void create_ShouldReturnCreatedProduct() throws Exception {
        when(productService.create(any(ProductRequest.class))).thenReturn(productResponse);

        mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(99.99));

        verify(productService, times(1)).create(any(ProductRequest.class));
    }

    @Test
    void findAll_ShouldReturnPageOfProducts() throws Exception {
        Page<ProductResponse> page = new PageImpl<>(Arrays.asList(productResponse));
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
        when(productService.findById("1")).thenReturn(productResponse);

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
        when(productService.findByName("Test Product")).thenReturn(productResponse);

        mockMvc.perform(get("/api/products/get/name/Test Product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).findByName("Test Product");
    }

    @Test
    void findByProductState_ShouldReturnListOfProducts() throws Exception {
        List<ProductResponse> products = Arrays.asList(productResponse);
        when(productService.findByProductState(ProductState.ACTIVE)).thenReturn(products);

        mockMvc.perform(get("/api/products/get/state/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productState").value("ACTIVE"));

        verify(productService, times(1)).findByProductState(ProductState.ACTIVE);
    }

    @Test
    void update_ShouldReturnUpdatedProduct() throws Exception {
        ProductRequest updatedRequest = new ProductRequest();
        updatedRequest.setName("Updated Product");
        updatedRequest.setPrice(149.99);

        ProductResponse updatedResponse = new ProductResponse();
        updatedResponse.setId("1");
        updatedResponse.setName("Updated Product");
        updatedResponse.setPrice(149.99);

        when(productService.update(eq("1"), any(ProductRequest.class))).thenReturn(updatedResponse);

        mockMvc.perform(put("/api/products/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk());

        verify(productService, times(1)).update(eq("1"), any(ProductRequest.class));
    }

    @Test
    void deleteById_ShouldReturnNoContent() throws Exception {
        doNothing().when(productService).deleteById("1");

        mockMvc.perform(delete("/api/products/delete/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteById("1");
    }
}