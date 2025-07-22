package com.fiap.postech.cliente_service.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Request para cadastrar um novo cliente.")
public class CadastraClienteRequest {

    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    @NotBlank(message = "Nome do cliente é obrigatório")
    private String nomeCliente;

    @Schema(description = "CPF do cliente (apenas números, 11 dígitos)", example = "12345678901")
    @NotBlank(message = "CPF do cliente é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpfCliente;

    @Schema(description = "Data de nascimento (formato yyyy-MM-dd). Deve ser uma data no passado.", example = "1995-08-15")
    @NotNull(message = "Data de nascimento é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @Schema(description = "Logradouro do endereço", example = "Rua das Palmeiras")
    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @Schema(description = "Número do endereço", example = "500")
    @NotBlank(message = "Número do endereço é obrigatório")
    private String numeroEndereco;

    @Schema(description = "CEP do endereço", example = "01234567")
    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @Schema(description = "Complemento do endereço", example = "Apto 101, Bloco B")
    private String complementoEndereco;

    @Schema(description = "Bairro", example = "Jardim Paulista")
    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @Schema(description = "Cidade", example = "São Paulo")
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @Schema(description = "Estado (UF, 2 letras)", example = "SP")
    @NotBlank(message = "Estado é obrigatório")
    private String estado;
}
