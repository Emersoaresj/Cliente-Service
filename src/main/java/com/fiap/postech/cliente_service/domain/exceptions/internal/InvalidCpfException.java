package com.fiap.postech.cliente_service.domain.exceptions.internal;

public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException(String message) {
        super(message);
    }
}
