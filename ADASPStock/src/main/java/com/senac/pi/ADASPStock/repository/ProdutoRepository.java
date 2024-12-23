
package com.senac.pi.ADASPStock.repository;

import com.senac.pi.ADASPStock.models.Produto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    public Optional<Produto> findById(Long id);
    
    public List<Produto> findByNomeContainingIgnoreCaseOrCategoriaNomeContainingIgnoreCase(String nome, String categoria);
    
}
