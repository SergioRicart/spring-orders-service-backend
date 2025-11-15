package com.rial.orderspring.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String property) {
        super("Client " + property + " not found");
    }

}
