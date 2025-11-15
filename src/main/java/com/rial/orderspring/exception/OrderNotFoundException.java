package com.rial.orderspring.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String property) {
        super("Order " + property + " not found");
    }

}
