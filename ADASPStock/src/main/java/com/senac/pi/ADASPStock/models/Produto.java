
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
    private int id;

    @Size(min = 1, max = 75)
    @Column(name = "nome", nullable = false, length = 75)
    @NotBlank
    private String nome;
    
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    @NotBlank
    private BigDecimal preco;
    
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    
    @Column(name = "descricao", length = 150)
    @Size(min = 1, max = 150)
    private String descricao;
    
    @ManyToOne(fetch = FetchType.LAZY) // Define a relação muitos-para-um com Categoria
    @JoinColumn(name = "categoria_id", nullable = false) // Chave estrangeira para a tabela Categoria
    private Categoria categoria;
}