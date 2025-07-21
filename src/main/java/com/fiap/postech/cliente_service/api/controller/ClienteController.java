package com.fiap.postech.cliente_service.api.controller;


import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ClienteRequest;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;
import com.fiap.postech.cliente_service.gateway.port.ClienteServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteServicePort service;

    @PostMapping
    public ResponseEntity<ResponseDto> cadastraCliente(@Valid @RequestBody ClienteRequest request) {
        ResponseDto cadastro = service.cadastraCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDto> buscarClientePorCpf(@PathVariable("cpf") String cpf) {
        ClienteDto cliente = service.buscarClientePorCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> listarClientes() {
        List<ClienteDto> clientes = service.listarTodos();
        return ResponseEntity.ok(clientes);
    }
}

