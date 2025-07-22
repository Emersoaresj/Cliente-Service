package com.fiap.postech.cliente_service.gateway.database;

import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;
import com.fiap.postech.cliente_service.api.mapper.ClienteMapper;
import com.fiap.postech.cliente_service.domain.exceptions.ErroInternoException;
import com.fiap.postech.cliente_service.domain.model.Cliente;
import com.fiap.postech.cliente_service.gateway.database.entity.ClienteEntity;
import com.fiap.postech.cliente_service.gateway.database.repository.ClienteRepositoryJPA;
import com.fiap.postech.cliente_service.gateway.port.ClienteRepositoryPort;
import com.fiap.postech.cliente_service.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryPort {

    @Autowired
    private ClienteRepositoryJPA clienteRepository;

    @Transactional
    @Override
    public ResponseDto cadastraCliente(Cliente cliente) {
        try {
            ClienteEntity clienteEntity = ClienteMapper.INSTANCE.domainToEntity(cliente);
            clienteRepository.save(clienteEntity);
            return montaResponse(clienteEntity, "cadastro");
        } catch (Exception e) {
            log.error("Erro ao cadastrar cliente", e);
            throw new ErroInternoException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @Override
    public Optional<Cliente> findByCpfCliente(String cpf) {
        try {
            return clienteRepository.findByCpfCliente(cpf)
                    .map(ClienteMapper.INSTANCE::entityToDomain);
        } catch (Exception e) {
            log.error("Erro ao buscar CPF", e);
            throw new ErroInternoException("Erro ao buscar CPF: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public ResponseDto atualizarClientePorCpf(Cliente request) {
        try {
            ClienteEntity clienteEntity = ClienteMapper.INSTANCE.UpdateDomainToEntity(request);
            clienteRepository.save(clienteEntity);
            return montaResponse(clienteEntity, "update");
        } catch (Exception e) {
            log.error("Erro ao cadastrar cliente", e);
            throw new ErroInternoException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deletarClientePorCpf(String cpf) {
        try {
            clienteRepository.deleteByCpfCliente(cpf);
        } catch (Exception e) {
            log.error("Erro ao deletar cliente", e);
            throw new ErroInternoException("Erro ao deletar cliente: " + e.getMessage());
        }

    }

    @Override
    public List<ClienteDto> listarTodos() {
        try {
            List<ClienteEntity> clientes = clienteRepository.findAll();
            return ClienteMapper.INSTANCE.domainToDtoList(clientes);
        } catch (Exception e) {
            log.error("Erro ao buscar clientes", e);
            throw new ErroInternoException("Erro ao buscar clientes no banco de dados: " + e.getMessage());
        }
    }

    private ResponseDto montaResponse(ClienteEntity clienteEntity, String acao) {
        ResponseDto response = new ResponseDto();

        if ("cadastro".equals(acao)) {
            response.setMessage(ConstantUtils.CLIENTE_CADASTRADO);
        } else {
            response.setMessage(ConstantUtils.CLIENTE_ATUALIZADO);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("nomeCliente", clienteEntity.getNomeCliente());
        data.put("cpfCliente", clienteEntity.getCpfCliente());
        data.put("dataNascimento", clienteEntity.getDataNascimento());

        response.setData(data);
        return response;
    }
}
