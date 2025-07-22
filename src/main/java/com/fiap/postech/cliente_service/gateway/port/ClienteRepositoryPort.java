package com.fiap.postech.cliente_service.gateway.port;

import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;
import com.fiap.postech.cliente_service.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {

    ResponseDto cadastraCliente(Cliente request);

    Optional<Cliente> findByCpfCliente(String cpf);

    ResponseDto atualizarClientePorCpf(Cliente request);

    void deletarClientePorCpf(String cpf);

    List<ClienteDto> listarTodos();
}
