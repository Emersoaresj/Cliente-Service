package com.fiap.postech.cliente_service.domain.exceptions.internal;

public class ClienteExistsException extends RuntimeException {
    public ClienteExistsException(String message) {
        super(message);
    }
}
