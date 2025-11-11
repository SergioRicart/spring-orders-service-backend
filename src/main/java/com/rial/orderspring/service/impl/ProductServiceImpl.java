package com.rial.orderspring.service.impl;

import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;
import com.rial.orderspring.repository.ProductRepository;
import com.rial.orderspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<List<Product>> findByProductState(ProductState productState) {
        return productRepository.findByProductState(productState);
    }

    @Override
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product update(String id, Product updatedProduct) throws Exception {
        Product actualProduct = productRepository.findById(id).orElseThrow(() -> new Exception("Producto con ID" +  id + " no Encontrado"));

        actualProduct.setName(updatedProduct.getName());
        actualProduct.setDescription(updatedProduct.getDescription());
        actualProduct.setPrice(updatedProduct.getPrice());
        actualProduct.setProductState(updatedProduct.getProductState());

        return productRepository.save(actualProduct);
    }

    @Override
    public void deleteById(String id) throws Exception {

        Product productoExistente = productRepository.findById(id).orElseThrow(() -> new Exception("Producto con ID" +  id + " no Encontrado"));

        productRepository.delete(productoExistente);

    }
}
