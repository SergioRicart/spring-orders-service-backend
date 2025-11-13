package com.rial.orderspring.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String property) {
        super("Product " + property + " not found");
    }

}
