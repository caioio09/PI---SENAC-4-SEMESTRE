package com.senac.pi.ADASPStock.service;

import com.senac.pi.ADASPStock.models.Produto;
import com.senac.pi.ADASPStock.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Método para criar um novo produto
    public Produto criarProduto(Produto produto) {
        if (produto.getCategoria() == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula");
        }
        return produtoRepository.save(produto);
    }

    // Método para listar todos os produtos
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    // Método para obter um produto por ID
    public Optional<Produto> obterProdutoPorId(int id) {
        return produtoRepository.findById(id);
    }

    // Método para atualizar um produto
    public Optional<Produto> atualizarProduto(int id, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setNome(produtoAtualizado.getNome());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setQuantidade(produtoAtualizado.getQuantidade());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setCategoria(produtoAtualizado.getCategoria());
            return Optional.of(produtoRepository.save(produto));
        }
        return Optional.empty();
    }

    // Método para deletar um produto
    public boolean deletarProduto(int id) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            produtoRepository.delete(produtoExistente.get());
            return true;
        }
        return false;
    }
}
