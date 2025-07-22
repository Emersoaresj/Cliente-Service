package com.fiap.postech.cliente_service.repositoryImpl;

import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;
import com.fiap.postech.cliente_service.api.mapper.ClienteMapper;
import com.fiap.postech.cliente_service.domain.exceptions.ErroInternoException;
import com.fiap.postech.cliente_service.domain.model.Cliente;
import com.fiap.postech.cliente_service.gateway.database.ClienteRepositoryImpl;
import com.fiap.postech.cliente_service.gateway.database.entity.ClienteEntity;
import com.fiap.postech.cliente_service.gateway.database.repository.ClienteRepositoryJPA;
import com.fiap.postech.cliente_service.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteRepositoryImplTest {

    @Mock
    private ClienteRepositoryJPA clienteRepository;

    @InjectMocks
    private ClienteRepositoryImpl repositoryImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------- CADASTRAR CLIENTE ----------
    @Test
    void cadastraClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("João");
        cliente.setCpfCliente("12345678901");
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
        ClienteEntity entity = ClienteMapper.INSTANCE.domainToEntity(cliente);

        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(entity);

        ResponseDto resp = repositoryImpl.cadastraCliente(cliente);

        assertEquals(ConstantUtils.CLIENTE_CADASTRADO, resp.getMessage());
        assertEquals("João", ((Map<?, ?>) resp.getData()).get("nomeCliente"));
    }

    @Test
    void cadastraClienteCatchException() {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("João");
        cliente.setCpfCliente("12345678901");
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
        ClienteEntity entity = ClienteMapper.INSTANCE.domainToEntity(cliente);

        when(clienteRepository.save(any(ClienteEntity.class))).thenThrow(new RuntimeException("erro"));

        ErroInternoException ex = assertThrows(ErroInternoException.class,
                () -> repositoryImpl.cadastraCliente(cliente));
        assertTrue(ex.getMessage().contains("erro"));
    }

    // ---------- FIND BY CPF ----------
    @Test
    void findByCpfClienteComSucesso() {
        ClienteEntity entity = new ClienteEntity();
        entity.setCpfCliente("12345678901");
        when(clienteRepository.findByCpfCliente("12345678901")).thenReturn(Optional.of(entity));

        Optional<Cliente> result = repositoryImpl.findByCpfCliente("12345678901");
        assertTrue(result.isPresent());
        assertEquals("12345678901", result.get().getCpfCliente());
    }

    @Test
    void findByCpfClienteCatchException() {
        when(clienteRepository.findByCpfCliente(anyString())).thenThrow(new RuntimeException("erro"));
        ErroInternoException ex = assertThrows(ErroInternoException.class,
                () -> repositoryImpl.findByCpfCliente("12345678901"));
        assertTrue(ex.getMessage().contains("erro"));
    }

    // ---------- ATUALIZAR CLIENTE ----------
    @Test
    void atualizarClientePorCpfComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Maria Atualizada");
        cliente.setCpfCliente("12345678901");
        cliente.setDataNascimento(LocalDate.of(1988, 3, 10));
        ClienteEntity entity = ClienteMapper.INSTANCE.UpdateDomainToEntity(cliente);

        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(entity);

        ResponseDto resp = repositoryImpl.atualizarClientePorCpf(cliente);

        assertEquals(ConstantUtils.CLIENTE_ATUALIZADO, resp.getMessage());
        assertEquals("Maria Atualizada", ((Map<?, ?>) resp.getData()).get("nomeCliente"));
    }

    @Test
    void atualizarClientePorCpfCatchException() {
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Maria Atualizada");
        cliente.setCpfCliente("12345678901");
        cliente.setDataNascimento(LocalDate.of(1988, 3, 10));
        ClienteEntity entity = ClienteMapper.INSTANCE.UpdateDomainToEntity(cliente);

        when(clienteRepository.save(any(ClienteEntity.class))).thenThrow(new RuntimeException("erro"));

        ErroInternoException ex = assertThrows(ErroInternoException.class,
                () -> repositoryImpl.atualizarClientePorCpf(cliente));
        assertTrue(ex.getMessage().contains("erro"));
    }

    // ---------- DELETAR CLIENTE ----------
    @Test
    void deletarClientePorCpfComSucesso() {
        doNothing().when(clienteRepository).deleteByCpfCliente("12345678901");
        assertDoesNotThrow(() -> repositoryImpl.deletarClientePorCpf("12345678901"));
        verify(clienteRepository).deleteByCpfCliente("12345678901");
    }

    @Test
    void deletarClientePorCpfCatchException() {
        doThrow(new RuntimeException("erro")).when(clienteRepository).deleteByCpfCliente(anyString());
        ErroInternoException ex = assertThrows(ErroInternoException.class,
                () -> repositoryImpl.deletarClientePorCpf("12345678901"));
        assertTrue(ex.getMessage().contains("erro"));
    }

    // ---------- LISTAR TODOS ----------
    @Test
    void listarTodosComSucesso() {
        ClienteEntity e1 = new ClienteEntity();
        ClienteEntity e2 = new ClienteEntity();
        List<ClienteEntity> entities = Arrays.asList(e1, e2);

        when(clienteRepository.findAll()).thenReturn(entities);

        List<ClienteDto> result = repositoryImpl.listarTodos();
        assertEquals(2, result.size());
    }

    @Test
    void listarTodosCatchException() {
        when(clienteRepository.findAll()).thenThrow(new RuntimeException("erro"));
        ErroInternoException ex = assertThrows(ErroInternoException.class,
                () -> repositoryImpl.listarTodos());
        assertTrue(ex.getMessage().contains("erro"));
    }
}
