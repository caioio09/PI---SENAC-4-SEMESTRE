package com.senac.pi.ADASPStock.service;

import com.senac.pi.ADASPStock.dto.EntradaEstoqueDTO;
import com.senac.pi.ADASPStock.repository.EntradaEstoqueRepository;
import com.senac.pi.ADASPStock.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.senac.pi.ADASPStock.models.EntradaEstoque;
import com.senac.pi.ADASPStock.models.Produto;

    @Service
    public class EstoqueService {

    @Autowired
    private EntradaEstoqueRepository entradaEstoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository; // Supondo que exista este repositório

    public void atualizarProduto(EntradaEstoqueDTO entradaDTO) {
        Produto produto = produtoRepository.findById(entradaDTO.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        produto.setQuantidade(produto.getQuantidade() + entradaDTO.getQuantidade());
        produtoRepository.save(produto);

        EntradaEstoque entrada = new EntradaEstoque();

        entrada.setProduto(produto);
        entrada.setQuantidade(entradaDTO.getQuantidade());
        entrada.setDataEntrada(entradaDTO.getDataEntrada());
        entrada.setFornecedor(entradaDTO.getFornecedor());

        entradaEstoqueRepository.save(entrada);
    }

    public List<EntradaEstoqueDTO> listarEntradas() {
        List<EntradaEstoque> entradas = entradaEstoqueRepository.findAll();
        return entradas.stream().map(entrada -> {
            EntradaEstoqueDTO dto = new EntradaEstoqueDTO();
            dto.setProductId(entrada.getProduto().getId());
            dto.setQuantidade(entrada.getQuantidade());
            dto.setDataEntrada(entrada.getDataEntrada());
            dto.setFornecedor(entrada.getFornecedor());
            return dto;
        }).toList();
    }
}