package sp.senac.br.sistema_estoque.controller;

import org.hibernate.event.spi.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import sp.senac.br.sistema_estoque.data.EstoqueRepository;
import sp.senac.br.sistema_estoque.model.Estoque;

@Controller
public class EstoqueController {
    
    @Autowired
    EstoqueRepository repository;
    

    @GetMapping("/")
    public String estoque(Model model, RedirectAttributes redirect){
        var produtos = repository.findAll();
        model.addAttribute("produtos", produtos);
        return "estoque";
    }


    @GetMapping("/formulario")
    public String formulario(){
        return "formulario";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Estoque estoque){
        repository.save(estoque);
        return "redirect:/";
    }

    @DeleteMapping("/")
    public String deletar(Long id, RedirectAttributes redirect){
        repository.deleteById(id);
        redirect.addFlashAttribute("mensagem", " Produto apagado com sucesso! ");
        return "redirect:/";
    }

    @GetMapping("/analise")
    public String analise(Long id,Model model){
        var produto = repository.findAll();
        model.addAttribute("produto", produto);
        model.addAttribute("idProduto", id);
        return "analise";
    }

}
