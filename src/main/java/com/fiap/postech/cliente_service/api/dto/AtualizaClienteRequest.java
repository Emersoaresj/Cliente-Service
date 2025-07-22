package com.fiap.postech.cliente_service.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Request para atualizar dados de um cliente. Só envie os campos que deseja alterar.")
public class AtualizaClienteRequest {

    @Schema(description = "Nome completo do cliente", example = "Maria Oliveira")
    private String nomeCliente;

    @Schema(description = "Data de nascimento (formato yyyy-MM-dd). Deve ser uma data no passado.",
            example = "1990-04-23")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
    private String logradouro;

    @Schema(description = "Número do endereço", example = "123")
    private String numeroEndereco;

    @Schema(description = "CEP do endereço", example = "12345678")
    private String cep;

    @Schema(description = "Complemento do endereço", example = "Apto 42")
    private String complementoEndereco;

    @Schema(description = "Bairro", example = "Centro")
    private String bairro;

    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado (UF, 2 letras)", example = "SP")
    private String estado;
}
