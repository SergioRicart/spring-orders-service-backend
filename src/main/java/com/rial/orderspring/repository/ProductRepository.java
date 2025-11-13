package com.rial.orderspring.repository;

import com.rial.orderspring.enums.ProductState;
import com.rial.orderspring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByName(String name);

    Optional<List<Product>> findByProductState(ProductState state);

}
