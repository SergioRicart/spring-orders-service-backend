package com.rial.orderspring;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String property) {
        super("Product " + property + " not found");
    }

}
