import java.math.BigDecimal;

public class Presidente {

    private BigDecimal valorDeCotas;

    public Presidente(BigDecimal valorDeCotas) {
        this.valorDeCotas = valorDeCotas;
    }

    public BigDecimal getValorDeCotas() {
        return valorDeCotas;
    }

    public void setValorDeCotas(BigDecimal valorDeCotas) {
        this.valorDeCotas = valorDeCotas;
    }

}
