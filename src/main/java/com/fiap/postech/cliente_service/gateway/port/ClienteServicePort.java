package com.fiap.postech.cliente_service.gateway.port;

import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ClienteRequest;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;

public interface ClienteServicePort {

    ResponseDto cadastraCliente(ClienteRequest request);
    ClienteDto buscarClientePorCpf(String cpf);
}
