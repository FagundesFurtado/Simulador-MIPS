package entity;

public enum EnumType {
    TipoR(0), TipoI(1), TipoJ(2), TipoD(3);

    public final int codigo;

    EnumType(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }
}
