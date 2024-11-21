package com.senac.pi.ADASPStock.repository;

import com.senac.pi.ADASPStock.models.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {
}
