package com.rial.orderspring.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String property) {
        super("User " + property + " not found");
    }
}
