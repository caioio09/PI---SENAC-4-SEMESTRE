package com.senac.pi.ADASPStock.service;

import com.senac.pi.ADASPStock.dto.EntradaEstoqueDTO;
import com.senac.pi.ADASPStock.dto.SaidaEstoqueDTO;
import com.senac.pi.ADASPStock.models.SaidaEstoque;
import com.senac.pi.ADASPStock.repository.EntradaEstoqueRepository;
import com.senac.pi.ADASPStock.repository.ProdutoRepository;
import com.senac.pi.ADASPStock.repository.SaidaEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.senac.pi.ADASPStock.models.EntradaEstoque;
import com.senac.pi.ADASPStock.models.Produto;

    @Service
    public class EstoqueService {

    @Autowired
    private SaidaEstoqueRepository saidaEstoqueRepository;

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


        public void atualizarProduto(SaidaEstoqueDTO saidaDTO) {
            Produto produto = produtoRepository.findById(saidaDTO.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            if(produto.getQuantidade() > saidaDTO.getQuantidade()) {
                produto.setQuantidade(produto.getQuantidade() - saidaDTO.getQuantidade());
                produtoRepository.save(produto);
            } else if (produto.getQuantidade() < saidaDTO.getQuantidade()) {
                throw new IllegalArgumentException("Quantidade Insuficiente");
            }
           SaidaEstoque saida = new SaidaEstoque();

            saida.setProduto(produto);
            saida.setQuantidade(saidaDTO.getQuantidade());
            saida.setDataSaida(saidaDTO.getDataSaida());
            saida.setDestino(saidaDTO.getDestino());

            saidaEstoqueRepository.save(saida);
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