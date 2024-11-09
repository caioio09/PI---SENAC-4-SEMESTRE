package com.senac.pi.ADASPStock.controllers;

import com.senac.pi.ADASPStock.models.Produto;
import com.senac.pi.ADASPStock.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;  // Injetando o serviço

    // Criar um novo produto
    @PostMapping("/produto")
    public Produto criarProduto(@RequestBody Produto produto) {
        try {
            return produtoService.criarProduto(produto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Obter todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    // Obter um produto pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterProduto(@PathVariable int id) {
        Optional<Produto> produto = produtoService.obterProdutoPorId(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar um produto pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable int id, @RequestBody Produto produtoAtualizado) {
        Optional<Produto> updatedProduto = produtoService.atualizarProduto(id, produtoAtualizado);
        return updatedProduto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Excluir um produto pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable int id) {
        boolean deleted = produtoService.deletarProduto(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
