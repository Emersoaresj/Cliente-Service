package com.fiap.postech.cliente_service.gateway.port;

import com.fiap.postech.cliente_service.api.dto.AtualizaClienteRequest;
import com.fiap.postech.cliente_service.api.dto.CadastraClienteRequest;
import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;

import java.util.List;

public interface ClienteServicePort {

    ResponseDto cadastraCliente(CadastraClienteRequest request);
    ClienteDto buscarClientePorCpf(String cpf);
    ResponseDto atualizarClientePorCpf(String cpf, AtualizaClienteRequest request);
    void deletarClientePorCpf(String cpf);
    List<ClienteDto> listarTodos();
}
