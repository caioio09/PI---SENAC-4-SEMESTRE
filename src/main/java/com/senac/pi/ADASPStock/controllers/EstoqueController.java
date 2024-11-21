package com.senac.pi.ADASPStock.controllers;

import com.senac.pi.ADASPStock.dto.EntradaEstoqueDTO;
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

    // Registrar uma nova entrada no estoque
    @PostMapping("/entrada")
    public ResponseEntity<String> registrarEntrada(@Valid @RequestBody EntradaEstoqueDTO entrada) {
        try {
            estoqueService.registrarEntrada(entrada);
            return ResponseEntity.ok("Entrada de estoque registrada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao registrar entrada: " + e.getMessage());
        }
    }

    // Listar entradas de estoque
    @GetMapping("/entradas")
    public ResponseEntity<List<EntradaEstoqueDTO>> listarEntradas() {
        List<EntradaEstoqueDTO> entradas = estoqueService.listarEntradas();
        return ResponseEntity.ok(entradas);
    }
}