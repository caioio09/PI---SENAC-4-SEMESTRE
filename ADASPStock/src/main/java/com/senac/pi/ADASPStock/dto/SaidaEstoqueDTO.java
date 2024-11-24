package com.senac.pi.ADASPStock.dto;

import java.math.BigDecimal;
import lombok.Data;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SaidaEstoqueDTO {


    private Long produtoId;
    private String produtoNome;
    private int quantidade;
    private LocalDateTime dataSaida;
    private String destino;
    private BigDecimal precoUnitario; // Use BigDecimal para consistência


}
