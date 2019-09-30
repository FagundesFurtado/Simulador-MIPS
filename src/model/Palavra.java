package model;

import entity.EnumType;

public class Palavra implements Cloneable {

    private String Palavra;
    private int Type;
    private int r1;
    private int r2;
    private int r3;
    private String op;
    private int offset;
    private int local;
    private int jump;
    private int temp;

    public Palavra() {
        Type = 0;
        Palavra = "";

    }

    @Override
    public Palavra clone() throws CloneNotSupportedException {
        return (Palavra) super.clone();
    }

    public Palavra(String p) {
        Type = 0;
        Palavra = p;

    }

    public String getPalavra() {
        return Palavra;
    }

    public void setPalavra(String Palavra) {
        this.Palavra = Palavra;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public void setType(EnumType enumType) {
        this.Type = enumType.getCodigo();
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getR3() {
        return r3;
    }

    public void setR3(int r3) {
        this.r3 = r3;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    public int getJump() {
        return jump;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    /**
     * @return the temp
     */
    public int getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(int temp) {
        this.temp = temp;
    }

}
