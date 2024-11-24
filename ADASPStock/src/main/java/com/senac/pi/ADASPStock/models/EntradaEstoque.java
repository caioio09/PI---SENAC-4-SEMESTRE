package com.senac.pi.ADASPStock.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EntradaEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produtoId", nullable = false)
    private Produto produto;

    private int quantidade;
    private LocalDateTime dataEntrada;
    private String fornecedor;

}
