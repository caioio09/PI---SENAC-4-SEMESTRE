package com.senac.pi.ADASPStock.controllers;

import com.senac.pi.ADASPStock.dto.EntradaEstoqueDTO;
import com.senac.pi.ADASPStock.dto.MovimentacaoEstoqueDTO;
import com.senac.pi.ADASPStock.dto.SaidaEstoqueDTO;
import com.senac.pi.ADASPStock.repository.SaidaEstoqueRepository;
import com.senac.pi.ADASPStock.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;
    
    @Autowired
    private SaidaEstoqueRepository saidaEstoqueRepository;


    @PutMapping("/entrada")
    public ResponseEntity<String> registrarEntrada(@Valid @RequestBody EntradaEstoqueDTO entrada) {
        try {
            estoqueService.atualizarProduto(entrada);
            return ResponseEntity.ok("Entrada de estoque registrada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao registrar entrada: " + e.getMessage());
        }
    }

    @PutMapping("/saida")
    public ResponseEntity<String> registrarSaida(@Valid @RequestBody SaidaEstoqueDTO saida) {
        try {
            estoqueService.atualizarProduto(saida);
            return ResponseEntity.ok("Saída de estoque registrada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao registrar saída: " + e.getMessage());
        }
    }

    @GetMapping("/entradas")
    public ResponseEntity<List<EntradaEstoqueDTO>> listarEntradas() {
        try {
            List<EntradaEstoqueDTO> entradas = estoqueService.listarEntradas();
            return ResponseEntity.ok(entradas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/saidas")
    public ResponseEntity<List<SaidaEstoqueDTO>> listarSaidas() {
        try {
            List<SaidaEstoqueDTO> saidas = estoqueService.listarSaidas();
            return ResponseEntity.ok(saidas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    @GetMapping("/movimentacoes")
        public ResponseEntity<List<MovimentacaoEstoqueDTO>> listarMovimentacoes() {
        try {
            List<MovimentacaoEstoqueDTO> movimentacoes = estoqueService.listarMovimentacoes();
            return ResponseEntity.ok(movimentacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
        
    @GetMapping("/mais-vendidos")
    public ResponseEntity<List<Object[]>> listarProdutosMaisVendidos() {
        try {
            // Aqui chamamos o método de forma não estática
            List<Object[]> maisVendidos = saidaEstoqueRepository.buscarProdutosMaisVendidos();
            return ResponseEntity.ok(maisVendidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
