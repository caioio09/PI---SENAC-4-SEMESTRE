package com.senac.pi.ADASPStock.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Setter
public class SaidaEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produtoId", nullable = false)
    private Produto produto;

    private int quantidade;
    private LocalDateTime dataSaida;
    private String destino;


}
