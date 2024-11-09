package com.senac.pi.ADASPStock.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Usuario.TABLE_NAME)  // Nome da tabela alterado para 'usuario'
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    
    public static final String TABLE_NAME = "usuario";  // Nome da tabela 'usuario'
    
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Size(min = 1, max = 75)
    @Column(name = "nome", nullable = false, length = 75)
    @NotBlank
    private String nome;
    
    @Column(name = "username", nullable = false)
    @Size(min = 3, max = 50)
    @NotBlank
    private String username;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Senha será excluída da visualização
    @Column(name = "password", length = 60, nullable = false)  // Campo 'password' renomeado
    @NotBlank
    @Size(min = 8, max = 20)
    private String senha;
    
    @Column(name = "cargo", nullable = false)
    @NotBlank
    private String cargo;

}
