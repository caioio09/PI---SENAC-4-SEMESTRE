package sp.senac.br.sistema_estoque.data;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.senac.br.sistema_estoque.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
    
}
