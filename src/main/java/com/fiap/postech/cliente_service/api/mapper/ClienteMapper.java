package com.fiap.postech.cliente_service.api.mapper;

import com.fiap.postech.cliente_service.api.dto.CadastraClienteRequest;
import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.domain.model.Cliente;
import com.fiap.postech.cliente_service.gateway.database.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "idCliente", ignore = true)
    ClienteEntity domainToEntity(Cliente cliente);

    ClienteEntity DeleteDomainToEntity(Cliente cliente);


    ClienteEntity UpdateDomainToEntity(Cliente cliente);

    Cliente entityToDomain(ClienteEntity entity);

    Cliente requestCreateToDomain(CadastraClienteRequest request);

    ClienteDto domainToDto(Cliente cliente);

    List<ClienteDto> domainToDtoList(List<ClienteEntity> clientes);

}
