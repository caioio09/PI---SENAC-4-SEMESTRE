package com.senac.pi.ADASPStock.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Produto.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produto {

    public static final String TABLE_NAME = "produto";

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 75)
    @Column(name = "nome", nullable = false, length = 75)
    @NotBlank
    private String nome;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "descricao", length = 150)
    @Size(min = 1, max = 150)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
