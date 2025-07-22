package com.fiap.postech.cliente_service.controller;


import com.fiap.postech.cliente_service.api.controller.ClienteController;
import com.fiap.postech.cliente_service.api.dto.AtualizaClienteRequest;
import com.fiap.postech.cliente_service.api.dto.CadastraClienteRequest;
import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;
import com.fiap.postech.cliente_service.gateway.port.ClienteServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteServicePort service;

    @InjectMocks
    private ClienteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastraCliente() {
        CadastraClienteRequest request = new CadastraClienteRequest();
        request.setNomeCliente("Maria");
        request.setCpfCliente("12345678901");
        // ...setar os outros campos necessários

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Cliente cadastrado com sucesso!");

        when(service.cadastraCliente(any(CadastraClienteRequest.class))).thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = controller.cadastraCliente(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Cliente cadastrado com sucesso!", response.getBody().getMessage());
        verify(service).cadastraCliente(request);
    }

    @Test
    void testBuscarClientePorCpf() {
        ClienteDto cliente = new ClienteDto();
        cliente.setIdCliente(1);
        cliente.setNomeCliente("Maria Oliveira");
        cliente.setCpfCliente("12345678901");
        cliente.setDataNascimento(LocalDate.of(1990, 4, 23));
        cliente.setCidade("São Paulo");
        cliente.setEstado("SP");

        when(service.buscarClientePorCpf("12345678901")).thenReturn(cliente);

        ResponseEntity<ClienteDto> response = controller.buscarClientePorCpf("12345678901");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Maria Oliveira", response.getBody().getNomeCliente());
        verify(service).buscarClientePorCpf("12345678901");
    }

    @Test
    void testListarClientes() {
        ClienteDto cliente1 = new ClienteDto();
        cliente1.setIdCliente(1);
        cliente1.setNomeCliente("Maria Oliveira");
        ClienteDto cliente2 = new ClienteDto();
        cliente2.setIdCliente(2);
        cliente2.setNomeCliente("João da Silva");
        List<ClienteDto> clientes = Arrays.asList(cliente1, cliente2);

        when(service.listarTodos()).thenReturn(clientes);

        ResponseEntity<List<ClienteDto>> response = controller.listarClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(service).listarTodos();
    }

    @Test
    void testAtualizarCliente() {
        AtualizaClienteRequest request = new AtualizaClienteRequest();
        request.setNomeCliente("Maria Atualizada");

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Cliente atualizado com sucesso!");

        when(service.atualizarClientePorCpf(eq("12345678901"), any(AtualizaClienteRequest.class)))
                .thenReturn(responseDto);

        ResponseEntity<ResponseDto> response = controller.atualizarCliente("12345678901", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente atualizado com sucesso!", response.getBody().getMessage());
        verify(service).atualizarClientePorCpf("12345678901", request);
    }

    @Test
    void testDeletarCliente() {
        doNothing().when(service).deletarClientePorCpf("12345678901");

        ResponseEntity<Void> response = controller.deletarCliente("12345678901");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service).deletarClientePorCpf("12345678901");
    }
}
