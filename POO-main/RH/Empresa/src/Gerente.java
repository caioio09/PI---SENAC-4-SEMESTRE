import java.math.BigDecimal;

public class Gerente extends Funcionario {

    private String area;
    private BigDecimal bonus;

    public Gerente(String area, BigDecimal bonus) {
        this.area = area;
        this.bonus = bonus;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
