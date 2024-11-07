package com.senac.pi.ADASPStock.controllers;

import com.senac.pi.ADASPStock.models.Usuario;
import com.senac.pi.ADASPStock.models.UsuarioError;
import com.senac.pi.ADASPStock.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;  // Injetando o serviço

    // Endpoint para listar todos os usuários
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // Endpoint para buscar um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable int id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para criar um novo usuário
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        try {
            // Tenta criar o usuário
            Usuario savedUsuario = usuarioService.createUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
        } catch (ResponseStatusException ex) {
            // Se a exceção for lançada, captura e retorna a mensagem de erro no formato JSON
            UsuarioError usuarioError = new UsuarioError(ex.getReason());
            return ResponseEntity.status(ex.getStatusCode()).body(usuarioError);
        }
    }

    // Endpoint para atualizar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable int id, @Valid @RequestBody Usuario usuarioDetails) {
        Optional<Usuario> updatedUsuario = usuarioService.updateUsuario(id, usuarioDetails);
        return updatedUsuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {
        boolean deleted = usuarioService.deleteUsuario(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
