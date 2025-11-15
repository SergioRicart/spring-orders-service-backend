package com.rial.orderspring.exception;

import java.time.LocalDateTime;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String property) {
        super("Order " + property + " not found");
    }

    public OrderNotFoundException(LocalDateTime property) {
        super("Order wirth date" + property + " not found");
    }

}
