package com.senac.pi.ADASPStock.controllers;

import com.senac.pi.ADASPStock.models.Login;
import com.senac.pi.ADASPStock.service.LoginService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Endpoint para realizar login
    @PostMapping
    public ResponseEntity<String> realizarLogin(@RequestParam String username, @RequestParam String senha) {
        String mensagem = loginService.realizarLogin(username, senha);
        if (mensagem.equals("Login realizado com sucesso!")) {
            return ResponseEntity.ok(mensagem);
        } else {
            return ResponseEntity.badRequest().body(mensagem);
        }
    }

    @GetMapping("/verificar-usuario-logado")
    public ResponseEntity<Boolean> verificarUsuarioLogado() {
        Optional<Login> login = loginService.obterUsuarioLogado();
        return ResponseEntity.ok(login.isPresent());
    }

    
    @GetMapping("/usuario-logado")
    public ResponseEntity<Login> obterUsuarioLogado() {
        // Retorna o único registro da tabela 'login'
        Optional<Login> login = loginService.obterUsuarioLogado();
        return login.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
    }
    
    @GetMapping("/verificar-cargo")
    public ResponseEntity<String> verificarCargo() {
        Optional<Login> login = loginService.obterUsuarioLogado();
        if (login.isPresent()) {
            return ResponseEntity.ok(login.get().getCargo());
        } else {
            return ResponseEntity.status(403).body("Acesso negado");
        }
    }

    // Endpoint para realizar logout
    @PostMapping("/logout")
    public ResponseEntity<String> realizarLogout() {
        loginService.realizarLogout();
        return ResponseEntity.ok("Logout realizado com sucesso!");
    }
}
