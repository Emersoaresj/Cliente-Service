package com.fiap.postech.cliente_service.api.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private String message;
    private Object data;
}
