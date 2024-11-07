package com.senac.pi.ADASPStock.service;

import com.senac.pi.ADASPStock.models.Usuario;
import com.senac.pi.ADASPStock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para listar todos os usuários
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para buscar um usuário por ID
    public Optional<Usuario> getUsuarioById(int id) {
        return usuarioRepository.findById(id);
    }

    // Método para criar um novo usuário
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para atualizar um usuário
    public Optional<Usuario> updateUsuario(int id, Usuario usuarioDetails) {
        Optional<Usuario> existingUsuario = usuarioRepository.findById(id);
        if (existingUsuario.isPresent()) {
            Usuario usuario = existingUsuario.get();
            usuario.setNome(usuarioDetails.getNome());
            usuario.setUsername(usuarioDetails.getUsername());
            usuario.setSenha(usuarioDetails.getSenha());
            usuario.setCargo(usuarioDetails.getCargo());
            return Optional.of(usuarioRepository.save(usuario));
        }
        return Optional.empty();
    }

    // Método para deletar um usuário
    public boolean deleteUsuario(int id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return true;
        }
        return false;
    }
}
