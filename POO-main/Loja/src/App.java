import java.math.BigDecimal;

public class App {
    public static void main(String[] args) throws Exception {
        var Produto1 = new Produto("Iphone", new BigDecimal(1000.50)); 
        System.out.println(Produto1.converterParaTexto());

        var livro = new Livro("Java", new BigDecimal(100), "Arana");
        System.out.println(livro.converterParaTexto());

        var eletronico = new Eletronico("Televisão",new BigDecimal(2000), "LG", "50 polegadas");
        System.out.println(eletronico.converterParaTexto());

    }
}
