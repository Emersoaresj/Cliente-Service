package com.fiap.postech.cliente_service.domain.exceptions.internal;

public class InvalidCepException extends RuntimeException {
    public InvalidCepException(String message) {
        super(message);
    }
}
