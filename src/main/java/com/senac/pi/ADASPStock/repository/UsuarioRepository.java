package com.senac.pi.ADASPStock.repository;

import com.senac.pi.ADASPStock.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método para verificar se o username já existe
    boolean existsByUsername(String username);
}
