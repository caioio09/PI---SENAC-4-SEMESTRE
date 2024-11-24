package com.senac.pi.ADASPStock;

import com.senac.pi.ADASPStock.models.Categoria;
import com.senac.pi.ADASPStock.models.Login;
import com.senac.pi.ADASPStock.repository.CategoriaRepository;
import com.senac.pi.ADASPStock.repository.LoginRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("dataInitializerBean")
public class DataInitializer {

    @Bean
    public CommandLineRunner dataInitializer(CategoriaRepository categoriaRepository, LoginRepository loginRepository) {
        return args -> {
            // Inicializa categorias
            if (categoriaRepository.count() == 0) {
                categoriaRepository.save(new Categoria(1, "Alimentos"));
                categoriaRepository.save(new Categoria(2, "Bebidas"));
                categoriaRepository.save(new Categoria(3, "Brinquedos"));
                categoriaRepository.save(new Categoria(4, "Cosméticos"));
                categoriaRepository.save(new Categoria(5, "Hortifruti"));
                categoriaRepository.save(new Categoria(6, "Eletrônicos"));
                categoriaRepository.save(new Categoria(7, "Roupas"));
                System.out.println("Categorias inseridas com sucesso!");
            } else {
                System.out.println("Categorias já existem no banco de dados.");
            }

            // Inicializa o registro na tabela "login"
            if (loginRepository.findById(1).isEmpty()) {
                Login login = new Login();
                login.setId(1); // ID fixo
                login.setUsername(null); // Inicialmente vazio
                login.setSenha(null);
                login.setCargo(null);
                loginRepository.save(login);
                System.out.println("Registro de login inicial criado com sucesso!");
            } else {
                System.out.println("Registro de login já existe no banco de dados.");
            }
        };
    }
}
