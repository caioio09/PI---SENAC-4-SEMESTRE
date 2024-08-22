import java.math.BigDecimal;

public abstract class Funcionario {

    private String nome;
    private int codigo;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    } 

    public void SalarioBase(BigDecimal salarioBase);

}
