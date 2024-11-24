package com.senac.pi.ADASPStock.service;

import com.senac.pi.ADASPStock.dto.EntradaEstoqueDTO;
import com.senac.pi.ADASPStock.dto.MovimentacaoEstoqueDTO;
import com.senac.pi.ADASPStock.dto.SaidaEstoqueDTO;
import com.senac.pi.ADASPStock.models.EntradaEstoque;
import com.senac.pi.ADASPStock.models.SaidaEstoque;
import com.senac.pi.ADASPStock.models.Produto;
import com.senac.pi.ADASPStock.repository.EntradaEstoqueRepository;
import com.senac.pi.ADASPStock.repository.ProdutoRepository;
import com.senac.pi.ADASPStock.repository.SaidaEstoqueRepository;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private EntradaEstoqueRepository entradaEstoqueRepository;

    @Autowired
    private SaidaEstoqueRepository saidaEstoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Registrar entrada no estoque
    public void atualizarProduto(EntradaEstoqueDTO entradaDTO) {
        Produto produto = produtoRepository.findById(entradaDTO.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        // Atualizar quantidade do produto
        produto.setQuantidade(produto.getQuantidade() + entradaDTO.getQuantidade());
        produtoRepository.save(produto);

        // Registrar a entrada no histórico
        EntradaEstoque entrada = new EntradaEstoque();
        entrada.setProduto(produto);
        entrada.setQuantidade(entradaDTO.getQuantidade());
        entrada.setDataEntrada(entradaDTO.getDataEntrada());
        entrada.setFornecedor(entradaDTO.getFornecedor());

        entradaEstoqueRepository.save(entrada);
    }

    // Registrar saída do estoque
    public void atualizarProduto(SaidaEstoqueDTO saidaDTO) {
        Produto produto = produtoRepository.findById(saidaDTO.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        // Verificar disponibilidade do estoque
        if (produto.getQuantidade() >= saidaDTO.getQuantidade()) {
            produto.setQuantidade(produto.getQuantidade() - saidaDTO.getQuantidade());
            produtoRepository.save(produto);
        } else {
            throw new IllegalArgumentException("Quantidade insuficiente no estoque");
        }

        // Registrar a saída no histórico
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
            dto.setProdutoId(entrada.getProduto().getId());
            dto.setProdutoNome(entrada.getProduto().getNome());
            dto.setQuantidade(entrada.getQuantidade());
            dto.setDataEntrada(entrada.getDataEntrada());
            dto.setFornecedor(entrada.getFornecedor());
            dto.setPrecoUnitario(entrada.getProduto().getPreco()); // Obtendo o preço unitário
            return dto;
        }).toList();
    }

    // Listar todas as saídas de estoque
    public List<SaidaEstoqueDTO> listarSaidas() {
        List<SaidaEstoque> saidas = saidaEstoqueRepository.findAll();
        return saidas.stream().map(saida -> {
            SaidaEstoqueDTO dto = new SaidaEstoqueDTO();
            dto.setProdutoId(saida.getProduto().getId());
            dto.setProdutoNome(saida.getProduto().getNome());
            dto.setQuantidade(saida.getQuantidade());
            dto.setDataSaida(saida.getDataSaida());
            dto.setPrecoUnitario(saida.getProduto().getPreco()); // Obtendo o preço unitário
            dto.setDestino(saida.getDestino());
            return dto;
        }).toList();
    }
    
    public List<MovimentacaoEstoqueDTO> listarMovimentacoes() {
    List<EntradaEstoque> entradas = entradaEstoqueRepository.findAll();
    List<SaidaEstoque> saidas = saidaEstoqueRepository.findAll();

    List<MovimentacaoEstoqueDTO> movimentacoes = new ArrayList<>();

    // Adiciona entradas
    for (EntradaEstoque entrada : entradas) {
        MovimentacaoEstoqueDTO dto = new MovimentacaoEstoqueDTO();
        dto.setTipo("Entrada");
        dto.setNomeProduto(entrada.getProduto().getNome());
        dto.setQuantidade(entrada.getQuantidade());
        dto.setData(entrada.getDataEntrada());
        dto.setFornecedorOuDestino(entrada.getFornecedor());
        movimentacoes.add(dto);
    }

    // Adiciona saídas
    for (SaidaEstoque saida : saidas) {
        MovimentacaoEstoqueDTO dto = new MovimentacaoEstoqueDTO();
        dto.setTipo("Saída");
        dto.setNomeProduto(saida.getProduto().getNome());
        dto.setQuantidade(saida.getQuantidade());
        dto.setData(saida.getDataSaida());
        dto.setFornecedorOuDestino(saida.getDestino());
        movimentacoes.add(dto);
    }

    return movimentacoes;
}

}