package com.senac.pi.ADASPStock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class EntradaEstoqueDTO {


    private long produtoId;
    private int quantidade;
    private LocalDateTime dataEntrada;
    private String fornecedor;

    public void setProductId(long productId) {
        this.produtoId = productId;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
}
