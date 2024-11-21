package com.senac.pi.ADASPStock.service;

import com.senac.pi.ADASPStock.models.EntradaEstoque;
import com.senac.pi.ADASPStock.repository.EntradaEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private EntradaEstoqueRepository entradaEstoqueRepository;

    public void registrarEntrada(EntradaEstoque entrada) {
        entradaEstoqueRepository.save(entrada);
    }

    public List<EntradaEstoque> listarEntradas() {
        return entradaEstoqueRepository.findAll();
    }
}
