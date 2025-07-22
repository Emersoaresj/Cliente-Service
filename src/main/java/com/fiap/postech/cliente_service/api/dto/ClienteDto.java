package com.fiap.postech.cliente_service.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "DTO de retorno para dados do cliente.")
public class ClienteDto {

    @Schema(description = "ID interno do cliente", example = "1")
    private Integer idCliente;

    @Schema(description = "Nome completo do cliente", example = "Maria Oliveira")
    private String nomeCliente;

    @Schema(description = "CPF do cliente (apenas números)", example = "12345678901")
    private String cpfCliente;

    @Schema(description = "Data de nascimento do cliente (formato yyyy-MM-dd)", example = "1990-04-23")
    private LocalDate dataNascimento;

    @Schema(description = "Cidade do cliente", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado (UF, 2 letras)", example = "SP")
    private String estado;
}
