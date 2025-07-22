package com.fiap.postech.cliente_service.api.controller;


import com.fiap.postech.cliente_service.api.dto.AtualizaClienteRequest;
import com.fiap.postech.cliente_service.api.dto.CadastraClienteRequest;
import com.fiap.postech.cliente_service.api.dto.ClienteDto;
import com.fiap.postech.cliente_service.api.dto.ResponseDto;
import com.fiap.postech.cliente_service.domain.exceptions.ErroInternoException;
import com.fiap.postech.cliente_service.domain.exceptions.internal.ClienteNotFoundException;
import com.fiap.postech.cliente_service.gateway.port.ClienteServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {

    @Autowired
    private ClienteServicePort service;

    @Operation(summary = "Cadastrar um novo cliente")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso",content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Cliente cadastrado com sucesso!\"}"))),

            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MethodArgumentNotValidException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Dados inválidos!\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroInternoException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno!\"}")))})
    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseDto> cadastraCliente(@Valid @RequestBody CadastraClienteRequest request) {
        ResponseDto cadastro = service.cadastraCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }


    @Operation(summary = "Buscar cliente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Dados do cliente retornados com sucesso.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteDto.class),
                    examples = @ExampleObject(value = """
                                  {
                                  "idCliente": 1,
                                  "nomeCliente": "Maria Oliveira",
                                  "cpfCliente": "12345678901",
                                  "dataNascimento": "1990-04-23",
                                  "cidade": "São Paulo",
                                  "estado": "SP"
                                }
                            """))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteNotFoundException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Cliente não encontrado!\"}"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MethodArgumentNotValidException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Dados inválidos!\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroInternoException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno!\"}")))})
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDto> buscarClientePorCpf(@Parameter(description = "CPF do cliente (apenas números)", example = "12345678901")
                                                              @PathVariable("cpf") String cpf) {
        ClienteDto cliente = service.buscarClientePorCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @Operation(summary = "Listar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Dados do cliente retornados com sucesso.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteDto.class),
                    examples = @ExampleObject(value = """
                                  {
                                  "idCliente": 1,
                                  "nomeCliente": "Maria Oliveira",
                                  "cpfCliente": "12345678901",
                                  "dataNascimento": "1990-04-23",
                                  "cidade": "São Paulo",
                                  "estado": "SP"
                                }
                            """))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteNotFoundException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Cliente não encontrado!\"}"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MethodArgumentNotValidException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Dados inválidos!\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroInternoException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno!\"}")))})
    @GetMapping
    public ResponseEntity<List<ClienteDto>> listarClientes() {
        List<ClienteDto> clientes = service.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @Operation(summary = "Atualizar cliente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso.", content = @Content(
                    schema = @Schema(implementation = ResponseDto.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Cliente atualizado com sucesso!\"}"))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClienteNotFoundException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Cliente não encontrado!\"}"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MethodArgumentNotValidException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Dados inválidos!\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErroInternoException.class),
                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno!\"}")))})
    @PutMapping("/cpf/{cpf}")
    public ResponseEntity<ResponseDto> atualizarCliente(@Parameter(description = "CPF do cliente (apenas números)", example = "12345678901")
                                                            @PathVariable("cpf") String cpf, @Valid @RequestBody AtualizaClienteRequest request) {
        ResponseDto atualizado = service.atualizarClientePorCpf(cpf, request);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    @Operation(summary = "Deletar cliente pelo CPF")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> deletarCliente(@Parameter(description = "CPF do cliente (apenas números)", example = "12345678901")
                                                   @PathVariable("cpf") String cpf) {
        service.deletarClientePorCpf(cpf);
        return ResponseEntity.noContent().build();
    }

}

