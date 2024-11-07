package com.senac.pi.ADASPStock.controllers;

import com.senac.pi.ADASPStock.models.Usuario;  // Alterado de 'User' para 'Usuario'
import com.senac.pi.ADASPStock.repository.UsuarioRepository;  // Alterado para 'UsuarioRepository'
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")  // Alterado para 'usuarios'
public class UsuarioController {  // Alterado para 'UsuarioController'

    @Autowired
    private UsuarioRepository usuarioRepository;  // Alterado para 'UsuarioRepository'

    // Endpoint para listar todos os usuários
    @GetMapping
    public List<Usuario> getAllUsuarios() {  // Alterado para 'Usuario'
        return usuarioRepository.findAll();
    }

    // Endpoint para buscar um usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable int id) {  // Alterado para 'Usuario'
        Optional<Usuario> usuario = usuarioRepository.findById(id);  // Alterado para 'Usuario'
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {  // Alterado para 'Usuario'
        Usuario savedUsuario = usuarioRepository.save(usuario);  // Alterado para 'Usuario'
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    // Endpoint para atualizar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable int id, @Valid @RequestBody Usuario usuarioDetails) {  // Alterado para 'Usuario'
        Optional<Usuario> existingUsuario = usuarioRepository.findById(id);  // Alterado para 'Usuario'
        if (existingUsuario.isPresent()) {
            Usuario usuario = existingUsuario.get();
            usuario.setNome(usuarioDetails.getNome());
            usuario.setUsername(usuarioDetails.getUsername());
            usuario.setSenha(usuarioDetails.getSenha());
            usuario.setCargo(usuarioDetails.getCargo());
            Usuario updatedUsuario = usuarioRepository.save(usuario);  // Alterado para 'Usuario'
            return ResponseEntity.ok(updatedUsuario);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {  // Alterado para 'Usuario'
        Optional<Usuario> usuario = usuarioRepository.findById(id);  // Alterado para 'Usuario'
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());  // Alterado para 'Usuario'
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
