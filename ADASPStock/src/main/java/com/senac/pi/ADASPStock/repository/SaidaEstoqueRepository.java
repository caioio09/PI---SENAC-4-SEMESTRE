package com.senac.pi.ADASPStock.repository;

import com.senac.pi.ADASPStock.models.SaidaEstoque;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaidaEstoqueRepository extends JpaRepository<SaidaEstoque, Long> {
    @Query("SELECT s.produto.nome, SUM(s.quantidade) AS totalVendido " +
       "FROM SaidaEstoque s " +
       "GROUP BY s.produto.nome " +
       "ORDER BY totalVendido DESC")
    List<Object[]> buscarProdutosMaisVendidos();

}
