public class Engenheiro extends Funcionario {

    private String departamento;
    private int crea;

    public Engenheiro(String departamento, int crea) {
        this.departamento = departamento;
        this.crea = crea;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getCrea() {
        return crea;
    }

    public void setCrea(int crea) {
        this.crea = crea;
    }

}
