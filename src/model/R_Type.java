//package model;
//
//public class R_Type extends Palavra {
//
//    private final boolean op[] = new boolean[6];
//    private final boolean rs[] = new boolean[5];
//    private final boolean rt[] = new boolean[5];
//    private final boolean rd[] = new boolean[5];
//    private final boolean shamt[] = new boolean[5];
//    private final boolean funct[] = new boolean[6];
//
//    public R_Type(boolean palavra[]) {
//        this.Palavra = palavra;
//        Split();
//
//    }
//
//    public void Split() {
//        System.arraycopy(Palavra, 0, op, 0, 6);
//        System.arraycopy(Palavra, 6, rs, 0, 5);
//        System.arraycopy(Palavra, 11, rt, 0, 5);
//        System.arraycopy(Palavra, 16, rd, 0, 5);
//        System.arraycopy(Palavra, 21, shamt, 0, 5);
//        System.arraycopy(Palavra, 26, funct, 0, 6);
//
//    }
//
//    public boolean[] getOp() {
//        return op;
//    }
//
//    public boolean[] getRs() {
//        return rs;
//    }
//
//    public boolean[] getRt() {
//        return rt;
//    }
//
//    public boolean[] getRd() {
//        return rd;
//    }
//
//    public boolean[] getShamt() {
//        return shamt;
//    }
//
//    public boolean[] getFunct() {
//        return funct;
//    }
//
//}
