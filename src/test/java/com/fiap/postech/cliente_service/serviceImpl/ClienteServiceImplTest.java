package com.fiap.postech.cliente_service.serviceImpl;

import com.fiap.postech.cliente_service.api.dto.*;
import com.fiap.postech.cliente_service.api.mapper.ClienteMapper;
import com.fiap.postech.cliente_service.domain.exceptions.ErroInternoException;
import com.fiap.postech.cliente_service.domain.exceptions.internal.*;
import com.fiap.postech.cliente_service.domain.model.Cliente;
import com.fiap.postech.cliente_service.gateway.port.ClienteRepositoryPort;
import com.fiap.postech.cliente_service.service.ClienteServiceImpl;
import com.fiap.postech.cliente_service.utils.ConstantUtils;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepositoryPort repositoryPort;

    @InjectMocks
    private ClienteServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helpers
    private CadastraClienteRequest mockCadastroRequest() {
        CadastraClienteRequest req = new CadastraClienteRequest();
        req.setNomeCliente("João");
        req.setCpfCliente("12345678901");
        req.setDataNascimento(LocalDate.of(1990, 1, 1));
        req.setCep("12345-678");
        req.setEstado("SP");
        req.setCidade("São Paulo");
        req.setBairro("Centro");
        req.setNumeroEndereco("100");
        req.setLogradouro("Rua A");
        return req;
    }

    private Cliente clienteComCpf(String cpf) {
        Cliente cliente = new Cliente();
        cliente.setCpfCliente(cpf);
        return cliente;
    }

    @Nested
    class CadastroClienteTests {

        @Test
        void cadastraClienteComSucesso() {
            CadastraClienteRequest req = mockCadastroRequest();
            when(repositoryPort.findByCpfCliente("12345678901")).thenReturn(Optional.empty());
            when(repositoryPort.cadastraCliente(any(Cliente.class))).thenReturn(criaResponse("Cliente cadastrado"));

            ResponseDto response = service.cadastraCliente(req);

            assertEquals("Cliente cadastrado", response.getMessage());
            verify(repositoryPort).cadastraCliente(any(Cliente.class));
        }

        @Test
        void cadastraClienteJaExistente() {
            CadastraClienteRequest req = mockCadastroRequest();
            Cliente cliente = ClienteMapper.INSTANCE.requestCreateToDomain(req);
            when(repositoryPort.findByCpfCliente("12345678901")).thenReturn(Optional.of(cliente));

            assertThrows(ClienteExistsException.class, () -> service.cadastraCliente(req));
        }

        @Test
        void cadastraClienteCatchExceptionLancaErroInterno() {
            CadastraClienteRequest req = mockCadastroRequest();
            when(repositoryPort.findByCpfCliente("12345678901")).thenReturn(Optional.empty());
            doThrow(new RuntimeException("erro inesperado")).when(repositoryPort).cadastraCliente(any(Cliente.class));

            ErroInternoException ex = assertThrows(ErroInternoException.class, () -> service.cadastraCliente(req));
            assertTrue(ex.getMessage().contains("erro inesperado"));
        }
    }

    @Nested
    class BuscarClienteTests {

        @Test
        void buscarPorCpfComSucesso() {
            when(repositoryPort.findByCpfCliente("12345678901")).thenReturn(Optional.of(clienteComCpf("12345678901")));
            ClienteDto dto = service.buscarClientePorCpf("12345678901");
            assertEquals("12345678901", dto.getCpfCliente());
        }

        @Test
        void buscarPorCpfInvalido() {
            assertThrows(ErroInternoException.class, () -> service.buscarClientePorCpf("cpfInvalido"));
        }

        @Test
        void buscarPorCpfNaoEncontrado() {
            when(repositoryPort.findByCpfCliente("99999999999")).thenReturn(Optional.empty());
            assertThrows(ClienteNotFoundException.class, () -> service.buscarClientePorCpf("99999999999"));
        }

        @Test
        void buscarPorCpfCatchException() {
            when(repositoryPort.findByCpfCliente(anyString())).thenThrow(new RuntimeException("erro inesperado"));
            ErroInternoException ex = assertThrows(ErroInternoException.class, () -> service.buscarClientePorCpf("12345678901"));
            assertTrue(ex.getMessage().contains("erro inesperado"));
        }
    }

    @Nested
    class AtualizaClienteTests {

        @Test
        void atualizaClienteComSucesso() {
            String cpf = "12345678901";
            AtualizaClienteRequest req = new AtualizaClienteRequest();
            req.setNomeCliente("Maria Atualizada");
            Cliente cliente = clienteComCpf(cpf);

            when(repositoryPort.findByCpfCliente(cpf)).thenReturn(Optional.of(cliente));
            when(repositoryPort.atualizarClientePorCpf(any(Cliente.class))).thenReturn(criaResponse("Cliente atualizado"));

            ResponseDto response = service.atualizarClientePorCpf(cpf, req);
            assertEquals("Cliente atualizado", response.getMessage());
        }

        @Test
        void atualizaClienteComCpfInvalido() {
            assertThrows(ErroInternoException.class, () -> service.atualizarClientePorCpf("abc", new AtualizaClienteRequest()));
        }

        @Test
        void atualizaClienteNaoEncontrado() {
            when(repositoryPort.findByCpfCliente("11111111111")).thenReturn(Optional.empty());
            assertThrows(ClienteNotFoundException.class, () -> service.atualizarClientePorCpf("11111111111", new AtualizaClienteRequest()));
        }

        @Test
        void atualizaClienteCatchException() {
            String cpf = "12345678901";
            AtualizaClienteRequest req = new AtualizaClienteRequest();
            Cliente cliente = clienteComCpf(cpf);

            when(repositoryPort.findByCpfCliente(cpf)).thenReturn(Optional.of(cliente));
            doThrow(new RuntimeException("erro inesperado")).when(repositoryPort).atualizarClientePorCpf(any(Cliente.class));

            ErroInternoException ex = assertThrows(ErroInternoException.class, () -> service.atualizarClientePorCpf(cpf, req));
            assertTrue(ex.getMessage().contains("erro inesperado"));
        }
    }

    @Nested
    class DeletaClienteTests {

        @Test
        void deletaClienteComSucesso() {
            String cpf = "12345678901";
            Cliente cliente = clienteComCpf(cpf);

            when(repositoryPort.findByCpfCliente(cpf)).thenReturn(Optional.of(cliente));
            doNothing().when(repositoryPort).deletarClientePorCpf(cpf);

            assertDoesNotThrow(() -> service.deletarClientePorCpf(cpf));
        }

        @Test
        void deletaClienteComCpfInvalido() {
            assertThrows(ErroInternoException.class, () -> service.deletarClientePorCpf("aaa"));
        }

        @Test
        void deletaClienteNaoEncontrado() {
            when(repositoryPort.findByCpfCliente("99999999999")).thenReturn(Optional.empty());
            assertThrows(ClienteNotFoundException.class, () -> service.deletarClientePorCpf("99999999999"));
        }

        @Test
        void deletaClienteCatchException() {
            String cpf = "12345678901";
            Cliente cliente = clienteComCpf(cpf);
            when(repositoryPort.findByCpfCliente(cpf)).thenReturn(Optional.of(cliente));
            doThrow(new RuntimeException("erro inesperado")).when(repositoryPort).deletarClientePorCpf(anyString());

            ErroInternoException ex = assertThrows(ErroInternoException.class, () -> service.deletarClientePorCpf(cpf));
            assertTrue(ex.getMessage().contains("erro inesperado"));
        }
    }

    @Nested
    class ListarClientesTests {
        @Test
        void listarTodosComSucesso() {
            ClienteDto c1 = new ClienteDto();
            ClienteDto c2 = new ClienteDto();
            List<ClienteDto> lista = Arrays.asList(c1, c2);

            when(repositoryPort.listarTodos()).thenReturn(lista);

            List<ClienteDto> result = service.listarTodos();
            assertEquals(2, result.size());
        }

        @Test
        void listarTodosCatchException() {
            when(repositoryPort.listarTodos()).thenThrow(new RuntimeException("erro"));
            assertThrows(ErroInternoException.class, () -> service.listarTodos());
        }
    }

    @Nested
    class ValidaClienteTests {

        @Test
        void deveLancarInvalidCpfException() {
            Cliente cliente = spy(new Cliente());
            doReturn(false).when(cliente).cpfFormatoValido();

            InvalidCpfException ex = assertThrows(InvalidCpfException.class,
                    () -> ReflectionTestUtils.invokeMethod(service, "validaCliente", cliente));
            assertEquals(ConstantUtils.CPF_INVALIDO, ex.getMessage());
        }

        @Test
        void deveLancarInvalidDataNascimentoException() {
            Cliente cliente = spy(new Cliente());
            doReturn(true).when(cliente).cpfFormatoValido();
            when(repositoryPort.findByCpfCliente(anyString())).thenReturn(Optional.empty());
            doReturn(false).when(cliente).nascimentoNaoEhFuturo();

            InvalidDataNascimentoException ex = assertThrows(InvalidDataNascimentoException.class,
                    () -> ReflectionTestUtils.invokeMethod(service, "validaCliente", cliente));
            assertEquals(ConstantUtils.DATA_NASCIMENTO_INVALIDA, ex.getMessage());
        }

        @Test
        void deveLancarInvalidCepException() {
            Cliente cliente = spy(new Cliente());
            doReturn(true).when(cliente).cpfFormatoValido();
            when(repositoryPort.findByCpfCliente(anyString())).thenReturn(Optional.empty());
            doReturn(true).when(cliente).nascimentoNaoEhFuturo();
            doReturn(false).when(cliente).cepFormatoValido();

            InvalidCepException ex = assertThrows(InvalidCepException.class,
                    () -> ReflectionTestUtils.invokeMethod(service, "validaCliente", cliente));
            assertEquals(ConstantUtils.CEP_INVALIDO, ex.getMessage());
        }

        @Test
        void deveLancarInvalidEstadoException() {
            Cliente cliente = spy(new Cliente());
            doReturn(true).when(cliente).cpfFormatoValido();
            when(repositoryPort.findByCpfCliente(anyString())).thenReturn(Optional.empty());
            doReturn(true).when(cliente).nascimentoNaoEhFuturo();
            doReturn(true).when(cliente).cepFormatoValido();
            doReturn(false).when(cliente).estadoFormatoValido();

            InvalidEstadoException ex = assertThrows(InvalidEstadoException.class,
                    () -> ReflectionTestUtils.invokeMethod(service, "validaCliente", cliente));
            assertEquals(ConstantUtils.ESTADO_INVALIDO, ex.getMessage());
        }
    }

    @Test
    void mergeClienteDeveAtualizarTodosOsCampos() {
        Cliente cliente = new Cliente();
        AtualizaClienteRequest req = new AtualizaClienteRequest();
        req.setNomeCliente("Maria Atualizada");
        req.setDataNascimento(LocalDate.of(2001, 2, 3));
        req.setLogradouro("Rua Nova");
        req.setNumeroEndereco("1234");
        req.setCep("11223344");
        req.setComplementoEndereco("Apto 12");
        req.setBairro("Bairro Legal");
        req.setCidade("Cidade Moderna");
        req.setEstado("RJ");

        ReflectionTestUtils.invokeMethod(service, "mergeCliente", cliente, req);

        assertEquals("Maria Atualizada", cliente.getNomeCliente());
        assertEquals(LocalDate.of(2001, 2, 3), cliente.getDataNascimento());
        assertEquals("Rua Nova", cliente.getLogradouro());
        assertEquals("1234", cliente.getNumeroEndereco());
        assertEquals("11223344", cliente.getCep());
        assertEquals("Apto 12", cliente.getComplementoEndereco());
        assertEquals("Bairro Legal", cliente.getBairro());
        assertEquals("Cidade Moderna", cliente.getCidade());
        assertEquals("RJ", cliente.getEstado());
    }

    // Utilitário para evitar repetição no mock de responses
    private ResponseDto criaResponse(String mensagem) {
        ResponseDto response = new ResponseDto();
        response.setMessage(mensagem);
        return response;
    }
}
