package com.senac.pi.ADASPStock.repository;

import com.senac.pi.ADASPStock.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Aqui você pode adicionar métodos personalizados, se necessário
}
