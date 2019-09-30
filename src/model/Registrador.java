package model;

public class Registrador {

    private int valor;
    private boolean usando;

    public Registrador() {
        valor = 0;
        usando = false;

    }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * @return the usando
     */
    public boolean isUsando() {
        return usando;
    }

    /**
     * @param usando the usando to set
     */
    public void setUsando(boolean usando) {
        this.usando = usando;
    }

}
