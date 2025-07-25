package com.fiap.postech.cliente_service.utils;

import lombok.Data;

@Data
public class ConstantUtils {


    private ConstantUtils() {
        throw new IllegalStateException("Classe Utilitária");
    }


    //ERROS
    public static final String CLIENTE_JA_EXISTE = "O cliente já está cadastrado!";
    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado para o CPF informado.";
    public static final String CPF_INVALIDO = "CPF inválido! O formato deve ser somente numérico e conter 11 dígitos.";
    public static final String DATA_NASCIMENTO_INVALIDA = "Data de nascimento inválida! A data não pode ser futura.";
    public static final String CEP_INVALIDO = "CEP inválido! O formato deve ser XXXXX-XXX";
    public static final String ESTADO_INVALIDO = "Estado inválido! O formato deve ser XX (sigla do estado)";

    //SUCESSO
    public static final String CLIENTE_CADASTRADO = "Cliente cadastrado com sucesso!";
    public static final String CLIENTE_ATUALIZADO = "Cliente atualizado com sucesso!";
}