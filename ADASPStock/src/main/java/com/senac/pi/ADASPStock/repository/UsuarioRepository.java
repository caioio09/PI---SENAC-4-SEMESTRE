package com.senac.pi.ADASPStock.repository;

import com.senac.pi.ADASPStock.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Métodos personalizados, se necessário
}
