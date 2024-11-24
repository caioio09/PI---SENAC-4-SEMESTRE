package com.senac.pi.ADASPStock.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MovimentacaoEstoqueDTO {
    private String tipo; // "Entrada" ou "Saída"
    private String nomeProduto;
    private int quantidade;
    private LocalDateTime data;
    private String fornecedorOuDestino;

}
