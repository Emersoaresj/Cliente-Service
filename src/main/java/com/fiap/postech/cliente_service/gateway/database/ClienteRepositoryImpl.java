package com.fiap.postech.cliente_service.gateway.database;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryPort {

    @Autowired
    private ClienteRepositoryJPA clienteRepository;

    @Override
    public ResponseDto cadastraCliente(Cliente cliente) {
        try {
            ClienteEntity clienteEntity = ClienteMapper.INSTANCE.domainToEntity(cliente);
            clienteRepository.save(clienteEntity);
            return montaResponse(clienteEntity);
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

    private ResponseDto montaResponse(ClienteEntity clienteEntity) {
        ResponseDto response = new ResponseDto();
        response.setMessage(ConstantUtils.CLIENTE_CADASTRADO);

        Map<String, Object> data = new HashMap<>();
        data.put("nomeCliente", clienteEntity.getNomeCliente());
        data.put("cpfCliente", clienteEntity.getCpfCliente());
        data.put("dataNascimento", clienteEntity.getDataNascimento());

        response.setData(data);
        return response;
    }
}
