package com.fiap.postech.cliente_service.domain.exceptions.internal;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
