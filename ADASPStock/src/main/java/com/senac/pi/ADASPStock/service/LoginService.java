package com.senac.pi.ADASPStock.service;

import com.senac.pi.ADASPStock.models.Login;
import com.senac.pi.ADASPStock.models.Usuario;
import com.senac.pi.ADASPStock.repository.LoginRepository;
import com.senac.pi.ADASPStock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LoginRepository loginRepository;

    /**
     * Método para realizar o login de um usuário
     * @param username Nome de usuário
     * @param senha Senha do usuário
     * @return Mensagem indicando sucesso ou erro
     */
    public String realizarLogin(String username, String senha) {
        // Verifica se o usuário existe na tabela de usuários
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);

        if (usuarioOptional.isEmpty()) {
            return "Usuário não existe.";
        }

        Usuario usuario = usuarioOptional.get();

        // Verifica se a senha está correta
        if (!usuario.getSenha().equals(senha)) {
            return "Senha incorreta.";
        }

        // Preenche a tabela 'login' com os dados do usuário (simula uma sessão)
        Login login = new Login();
        login.setId(1); // Sempre substitui o único registro existente
        login.setUsername(usuario.getUsername());
        login.setSenha(usuario.getSenha());
        login.setCargo(usuario.getCargo());
        loginRepository.save(login);

        return "Login realizado com sucesso!";
    }

    /**
     * Método para realizar o logout
     */
    public void realizarLogout() {
        // Limpa o único registro na tabela 'login'
        loginRepository.deleteById(1);
    }
    
    public Optional<Login> obterUsuarioLogado() {
        return loginRepository.findById(1); // Obtém o único registro da tabela 'login'
    }

}
