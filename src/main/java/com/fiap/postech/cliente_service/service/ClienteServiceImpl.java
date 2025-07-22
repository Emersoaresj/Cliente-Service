package com.fiap.postech.cliente_service.service;

import com.fiap.postech.cliente_service.api.dto.AtualizaClienteRequest;
import com.fiap.postech.cliente_service.api.dto.CadastraClienteRequest;
import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;
import com.fiap.postech.cliente_service.api.mapper.ClienteMapper;
import com.fiap.postech.cliente_service.domain.exceptions.ErroInternoException;
import com.fiap.postech.cliente_service.domain.exceptions.internal.*;
import com.fiap.postech.cliente_service.domain.model.Cliente;
import com.fiap.postech.cliente_service.gateway.port.ClienteRepositoryPort;
import com.fiap.postech.cliente_service.gateway.port.ClienteServicePort;
import com.fiap.postech.cliente_service.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteServicePort {

    @Autowired
    private ClienteRepositoryPort repositoryPort;


    @Override
    public ResponseDto cadastraCliente(CadastraClienteRequest request) {

        try {
            Cliente cliente = ClienteMapper.INSTANCE.requestCreateToDomain(request);

            validaCliente(cliente);

            return repositoryPort.cadastraCliente(cliente);

        } catch (InvalidCpfException | ClienteExistsException | InvalidDataNascimentoException |
                 InvalidCepException | InvalidEstadoException e) {
            throw e;

        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar cliente", e);
            throw new ErroInternoException("Erro interno ao tentar cadastrar cliente: " + e.getMessage());
        }
    }

    @Override
    public ClienteDto buscarClientePorCpf(String cpf) {
        if (!cpf.matches("\\d+")) {
            throw new ErroInternoException("O CPF deve conter apenas números.");
        }

        try {

            Cliente cliente = repositoryPort.findByCpfCliente(cpf)
                    .orElseThrow(() -> new ClienteNotFoundException(ConstantUtils.CLIENTE_NAO_ENCONTRADO));
            return ClienteMapper.INSTANCE.domainToDto(cliente);

        } catch (ClienteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar cliente", e);
            throw new ErroInternoException("Erro interno ao tentar buscar cliente: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto atualizarClientePorCpf(String cpf, AtualizaClienteRequest request) {

        if (!cpf.matches("\\d+")) {
            throw new ErroInternoException("O CPF deve conter apenas números.");
        }

        try {
            Cliente cliente = repositoryPort.findByCpfCliente(cpf)
                    .orElseThrow(() -> new ClienteNotFoundException(ConstantUtils.CLIENTE_NAO_ENCONTRADO));

            mergeCliente(cliente, request);

            return repositoryPort.atualizarClientePorCpf(cliente);
        } catch (ClienteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar cliente", e);
            throw new ErroInternoException("Erro interno ao tentar atualizar cliente: " + e.getMessage());
        }
    }


    @Override
    public void deletarClientePorCpf(String cpf) {

        if (!cpf.matches("\\d+")) {
            throw new ErroInternoException("O CPF deve conter apenas números.");
        }

        try {
            Cliente cliente = repositoryPort.findByCpfCliente(cpf)
                    .orElseThrow(() -> new ClienteNotFoundException(ConstantUtils.CLIENTE_NAO_ENCONTRADO));

            repositoryPort.deletarClientePorCpf(cliente.getCpfCliente());

        } catch (ClienteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar cliente", e);
            throw new ErroInternoException("Erro interno ao tentar deletar cliente: " + e.getMessage());
        }
    }

    @Override
    public List<ClienteDto> listarTodos() {
        try {
            return repositoryPort.listarTodos();
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar clientes", e);
            throw new ErroInternoException("Erro interno ao tentar buscar clientes: " + e.getMessage());
        }
    }

    private void validaCliente(Cliente cliente) {
        if (!cliente.cpfFormatoValido()) {
            log.warn("CPF com formato inválido: {}", cliente.getCpfCliente());
            throw new InvalidCpfException(ConstantUtils.CPF_INVALIDO);
        }

        if (repositoryPort.findByCpfCliente(cliente.getCpfCliente()).isPresent()) {
            log.warn("Cliente já cadastrado com o CPF: {}", cliente.getCpfCliente());
            throw new ClienteExistsException(ConstantUtils.CLIENTE_JA_EXISTE);
        }

        if (!cliente.nascimentoNaoEhFuturo()) {
            log.warn("Data de nascimento não pode ser no futuro: {}", cliente.getDataNascimento());
            throw new InvalidDataNascimentoException(ConstantUtils.DATA_NASCIMENTO_INVALIDA);
        }

        if (!cliente.cepFormatoValido()) {
            log.warn("CEP com formato inválido: {}", cliente.getCep());
            throw new InvalidCepException(ConstantUtils.CEP_INVALIDO);
        }

        if (!cliente.estadoFormatoValido()) {
            log.warn("Estado com formato inválido: {}", cliente.getEstado());
            throw new InvalidEstadoException(ConstantUtils.ESTADO_INVALIDO);
        }
    }

    private void mergeCliente(Cliente cliente, AtualizaClienteRequest request) {
        if (request.getNomeCliente() != null) {
            cliente.setNomeCliente(request.getNomeCliente());
        }
        if (request.getDataNascimento() != null) {
            cliente.setDataNascimento(request.getDataNascimento());
        }
        if (request.getLogradouro() != null) {
            cliente.setLogradouro(request.getLogradouro());
        }
        if (request.getNumeroEndereco() != null) {
            cliente.setNumeroEndereco(request.getNumeroEndereco());
        }
        if (request.getCep() != null) {
            cliente.setCep(request.getCep());
        }
        if (request.getComplementoEndereco() != null) {
            cliente.setComplementoEndereco(request.getComplementoEndereco());
        }
        if (request.getBairro() != null) {
            cliente.setBairro(request.getBairro());
        }
        if (request.getCidade() != null) {
            cliente.setCidade(request.getCidade());
        }
        if (request.getEstado() != null) {
            cliente.setEstado(request.getEstado());
        }
    }

}
