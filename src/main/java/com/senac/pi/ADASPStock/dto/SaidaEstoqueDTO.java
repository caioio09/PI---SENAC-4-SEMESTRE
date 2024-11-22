package com.senac.pi.ADASPStock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class SaidaEstoqueDTO {


    private long produtoId;
    private int quantidade;
    private LocalDateTime dataSaida;
    private String destino;

    public void setProductId(long productId) {
        this.produtoId = productId;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
