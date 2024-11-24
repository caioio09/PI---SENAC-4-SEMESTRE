package com.senac.pi.ADASPStock.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EntradaEstoqueDTO {
    private Long produtoId;
    private String produtoNome;
    private int quantidade;
    private LocalDateTime dataEntrada;
    private String fornecedor;
    private BigDecimal precoUnitario; // Use BigDecimal para consistência

}
