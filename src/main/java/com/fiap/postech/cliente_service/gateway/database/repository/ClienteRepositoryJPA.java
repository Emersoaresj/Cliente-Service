package com.fiap.postech.cliente_service.gateway.database.repository;

import com.fiap.postech.cliente_service.gateway.database.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepositoryJPA extends JpaRepository<ClienteEntity, Integer> {

    Optional<ClienteEntity> findByCpfCliente(String cpf);

}
