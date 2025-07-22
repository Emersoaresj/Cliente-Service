package com.fiap.postech.cliente_service.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resposta padrão de mensagens do sistema.")
public class ResponseDto {

    @Schema(description = "Mensagem de resposta")
    private String message;
    @Schema(description = "Dados adicionais da resposta, se houver")
    private Object data;
}
