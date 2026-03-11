package com.rial.orderspring.mapper;

import com.rial.orderspring.dto.ProductRequest;
import com.rial.orderspring.dto.ProductResponse;
import com.rial.orderspring.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setProductState(product.getProductState());
        return response;
    }

    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setProductState(request.getProductState());
        return product;
    }
}
