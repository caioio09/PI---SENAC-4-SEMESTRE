package com.senac.pi.ADASPStock;

import com.senac.pi.ADASPStock.models.Categoria;
import com.senac.pi.ADASPStock.repository.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("dataInitializerBean")
public class DataInitializer {

    @Bean
    public CommandLineRunner dataInitializer(CategoriaRepository categoriaRepository) {
        return args -> {
            // Verifique se os dados já estão presentes para evitar duplicação
            if (categoriaRepository.count() == 0) {
                // Insira as categorias manualmente
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
        };
    }
}
