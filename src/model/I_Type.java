//package model;
//
//public class I_Type extends Palavra {
//
//    private final boolean op[] = new boolean[6];
//    private final boolean rs[] = new boolean[5];
//    private final boolean rt[] = new boolean[5];
//    private final boolean immediate[] = new boolean[16];
//
//    public I_Type(boolean palavra[]) {
//        this.Palavra = palavra;
//        Split();
//
//    }
//
//    public void Split() {
//        System.arraycopy(Palavra, 0, op, 0, 6);
//        System.arraycopy(Palavra, 6, rs, 0, 5);
//        System.arraycopy(Palavra, 11, rt, 0, 5);
//        System.arraycopy(Palavra, 16, immediate, 0, 16);
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
//    public boolean[] getImmediate() {
//        return immediate;
//    }
//
//}
