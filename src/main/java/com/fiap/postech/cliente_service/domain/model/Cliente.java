package com.fiap.postech.cliente_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {

    private Integer idCliente;
    private String nomeCliente;
    private String cpfCliente;
    private LocalDate dataNascimento;
    private String logradouro;
    private String numeroEndereco;
    private String cep;
    private String complementoEndereco;
    private String bairro;
    private String cidade;
    private String estado;

    public boolean cpfFormatoValido() {
        return cpfCliente != null && cpfCliente.matches("\\d{11}");
    }

    public boolean nascimentoNaoEhFuturo() {
        return dataNascimento != null && !dataNascimento.isAfter(LocalDate.now());
    }

    public boolean cepFormatoValido() {
        return cep != null && cep.matches("\\d{5}-\\d{3}");
    }

    public boolean estadoFormatoValido() {
        return estado != null && estado.matches("[A-Z]{2}");
    }
}

