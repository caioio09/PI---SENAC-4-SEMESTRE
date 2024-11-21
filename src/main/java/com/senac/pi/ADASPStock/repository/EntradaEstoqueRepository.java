package com.senac.pi.ADASPStock.repository;

import com.senac.pi.ADASPStock.dto.EntradaEstoqueDTO;
import com.senac.pi.ADASPStock.models.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {
}
