package com.fiap.postech.cliente_service.api.mapper;

import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ClienteRequest;
import com.fiap.postech.cliente_service.domain.model.Cliente;
import com.fiap.postech.cliente_service.gateway.database.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "idCliente", ignore = true)
    ClienteEntity domainToEntity(Cliente cliente);

    Cliente entityToDomain(ClienteEntity entity);

    Cliente requestToDomain(ClienteRequest request);

    ClienteDto domainToDto(Cliente cliente);

}
