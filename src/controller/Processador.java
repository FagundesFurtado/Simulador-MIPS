package controller;

import entity.EnumType;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Palavra;
import model.Registrador;
import view.getTC;

public class Processador {

    public int PC;
    public int CLOCK = 0;
    public boolean Forwarding;
    public ArrayList<Registrador> registradores;
    private Palavra busca_decod;
    private Palavra decod_ula;
    private Palavra ula_memoria;
    private Palavra memoria_writeback;
    private Palavra writeback;
    private Palavra buscaOperando;
    public String Memoria[];
    private ArrayList<String> Instrucao;
    public ArrayList<StringBuilder> InstrucoesBinarias;
    private boolean busca = true;
    private boolean decod = true;
    private boolean ula = true;
    private boolean jump = false;
    private SimuladorMIPS m;

    public Processador(ArrayList<String> instrucao, SimuladorMIPS m) {
        PC = 0;
        this.m = m;
        this.Instrucao = instrucao;
        InstrucoesBinarias = new ArrayList<>();
        registradores = new ArrayList<>();
        Forwarding = false;
        Memoria = new String[1024];
        for (int i = 0; i < 34; i++) {
            Registrador r = new Registrador();
            registradores.add(r);
        }

        registradores.get(0).setValor(0);

        Instrucao.add("end");
        Instrucao.add("end");
        Instrucao.add("end");
        Instrucao.add("end");
        Instrucao.add("end");
        buscaOperando = new Palavra("noop");
        busca_decod = new Palavra("noop");
        decod_ula = new Palavra("noop");
        ula_memoria = new Palavra("noop");
        memoria_writeback = new Palavra("noop");
        writeback = new Palavra("noop");

//        
//        for (int i = 0; i < Instrucao.size(); i++) {
//            leituraDeInstrucao();
//        }
//        PC = 0;
        Registrador r = new Registrador();
        Registrador r1 = new Registrador();

    }

    private void verificaBloqueados() {

        ArrayList<Integer> lock = new ArrayList<>();

        for (int i = 0; i < registradores.size(); i++) {
            if (registradores.get(i).isUsando()) {
                lock.add(i);
            }

        }

        System.out.println(lock.toString());

    }

    public void geraBinario() {

        for (int i = 0; i < Instrucao.size(); i++) {
            String teste = Instrucao.get(i);
            teste = teste.replaceFirst(" ", ",");
            teste = teste.replaceAll(" ", "");
            String aux[] = teste.split(",");

            if (aux[0].equals("add") || aux[0].equals("sub") || aux[0].equals("mult") || aux[0].equals("div")) {
                R_TypeBinary(aux);

                //tipo R
            } else {
                //TIPO I
                if (aux[0].equals("bne") || aux[0].equals("beq") || aux[0].equals("lw") || aux[0].equals("sw") || aux[0].equals("bltz") || aux[0].equals("bgtz")) {
                    I_TypeBinary(aux);
                } else {
                    //Tipo J
                    if (aux[0].equals("j") || aux[0].equals("jr")) {
                        J_TypeBinary2(aux);
                    } else {
                        //Tipo D
                        if (aux[0].equals("noop") || aux[0].equals("get_tc")) {
                            D_TypeBinary(aux);
                        }

                    }

                }

            }

            for (int j = 0; j < aux.length; j++) {
                System.out.print(" " + aux[j]);

            }
            System.out.println("");

        }
        m.GravaEmDisco();
        for (StringBuilder i : InstrucoesBinarias) {
            System.out.println(i);
        }

    }

    public ArrayList<String> NextOperation() {

        ArrayList<String> aux = new ArrayList<>();
        if (PC == Instrucao.size() - 1) {
            JOptionPane.showMessageDialog(null, "Fim de execução");

        } else {

            // if (PC == Instrucao.size() - 1) {
            writeBack();
            memoria();
            ULA();
            if (!jump) {
                decodificacao();
                buscaDeInstrucao();

            } else {

                PC--;
            }

            unlockRegisters(writeback);
            verificaBloqueados();

            jump = false;

            //}
            if (busca) {
                PC++;
            }
            CLOCK++;
//            System.out.println("Busca: " + busca_decod.getPalavra());
//            System.out.println("Decod: " + decod_ula.getPalavra());
//            System.out.println("ULA: " + ula_memoria.getPalavra());
//            System.out.println("Memoria: " + memoria_writeback.getPalavra());
//            System.out.println("WriteBack: " + writeback.getPalavra());
//            System.out.println("\n");

            aux.add(busca_decod.getPalavra());
            aux.add(decod_ula.getPalavra());
            aux.add(ula_memoria.getPalavra());
            aux.add(memoria_writeback.getPalavra());
            aux.add(writeback.getPalavra());
            aux.add(String.valueOf(PC));
            aux.add(String.valueOf(CLOCK));
            aux.add(String.valueOf(Forwarding));
            aux.add(reg());

        }
        return aux;
    }

    private void unlockRegisters(Palavra p) {
        registradores.get(p.getR1()).setUsando(false);
        registradores.get(p.getR2()).setUsando(false);
        registradores.get(p.getR3()).setUsando(false);

    }

    private String reg() {

        String registradores = "";

        registradores = registradores + "\n";
        for (int i = 0; i < this.registradores.size(); i++) {
            registradores = registradores + "  " + this.registradores.get(i).getValor();
        }

        return registradores;
    }

    private void buscaDeInstrucao() {

        if (Forwarding) {
            busca_decod.setPalavra(Instrucao.get(PC).replaceFirst(" ", ","));
            busca_decod.setPalavra(busca_decod.getPalavra().replaceAll(" ", ""));
        } else {
            if (busca && busca_decod.getPalavra().equals("noop")) {
                busca_decod.setPalavra(Instrucao.get(PC).replaceFirst(" ", ","));
                busca_decod.setPalavra(busca_decod.getPalavra().replaceAll(" ", ""));
            } else {
                if (busca) {

                    busca_decod.setPalavra(Instrucao.get(PC).replaceFirst(" ", ","));
                    busca_decod.setPalavra(busca_decod.getPalavra().replaceAll(" ", ""));
                }
            }

        }
        // System.out.println("Busca " + busca_decod.getPalavra());

    }

    private void decodificacao() {

        if (Forwarding) {
            codeDecod();
        } else {
            if (decod && !busca_decod.getPalavra().equals("noop")) {
                if (!busca_decod.getPalavra().equals("end")) {
                    codeDecod();
                } else {
                    decod_ula.setPalavra("end");

                }
            }
        }
    }

    private void codeDecod() {

        String aux[] = busca_decod.getPalavra().split(",");
        if (aux[0].equals("add") || aux[0].equals("sub") || aux[0].equals("mult") || aux[0].equals("div")) {
            busca_decod.setType(EnumType.TipoR);
            //Integer.parseInt(aux[1].substring(1, aux[1].length()));
            busca_decod.setR1(Integer.parseInt(aux[1].substring(1, aux[1].length())));
            busca_decod.setR2(Integer.parseInt(aux[2].substring(1, aux[2].length())));
            busca_decod.setR3(Integer.parseInt(aux[3].substring(1, aux[3].length())));
            busca_decod.setOp(aux[0]);
            //  R_TypeBinary(busca_decod);

        } else {
            if (aux[0].equals("lw") || aux[0].equals("sw")) {
                busca_decod.setType(EnumType.TipoI);
                busca_decod.setOp(aux[0]);
                busca_decod.setR1(Integer.parseInt(aux[1].substring(1, aux[1].length())));

                String aux2[] = aux[2].split("\\(");
                busca_decod.setR2(Integer.parseInt(aux2[1].substring(1, aux2[1].length() - 1)));
                busca_decod.setOffset(Integer.parseInt(aux2[0].replaceAll(" ", "")));

                //busca_decod.setOffset(Integer.parseInt(aux[2]));
                //    I_TypeBinary(busca_decod);
//                I_TypeOperation(aux);
            } else {
                if (aux[0].equals("bne") || aux[0].equals("beq") || aux[0].equals("bltz") || aux[0].equals("bgtz")) {

                    busca_decod.setType(EnumType.TipoI);
                    busca_decod.setOp(aux[0]);
                    busca_decod.setR1(Integer.parseInt(aux[1].substring(1, aux[1].length())));
                    busca_decod.setR2(Integer.parseInt(aux[2].substring(1, aux[2].length())));

                    busca_decod.setOffset(Integer.parseInt(aux[3]));

                } else {

                    if (aux[0].equals("jr") || aux[0].equals("j")) {
                        busca_decod.setType(EnumType.TipoJ);
                        busca_decod.setJump(Integer.parseInt(aux[1]));
                        busca_decod.setOp(aux[0]);
                        //      J_TypeBinary(busca_decod);
                        // J_TypeOperation(aux);
                    } else {
                        if (aux[0].equals("get_tc")) {

                            busca_decod.setType(EnumType.TipoD);
                            busca_decod.setTemp(Integer.parseInt(aux[1]));
                        }
                    }
                }
            }
        }
        decod_ula = busca_decod;
        //  System.out.println("Decod: " + decod_ula.getPalavra());
        busca_decod = new Palavra("noop");

    }

    private void lockRegister(Palavra p) {

        //  if (!p.getOp().equals("bnq") && !p.getOp().equals("bnq")) {
        registradores.get(p.getR1()).setUsando(true);
        registradores.get(p.getR2()).setUsando(true);
        registradores.get(p.getR3()).setUsando(true);

        // }
    }

    private void ULA() {
        if (!decod_ula.getPalavra().equals("end")) {
            if (Forwarding) {
                codeULA();
            } else {
                if (!registradores.get(decod_ula.getR1()).isUsando()
                        && !registradores.get(decod_ula.getR2()).isUsando()
                        && !registradores.get(decod_ula.getR3()).isUsando()) {
                    lockRegister(decod_ula);
                    codeULA();
                    busca = true;
                    decod = true;
                } else {
                    busca = false;
                    decod = false;
                }
            }
        } else {
            ula_memoria.setPalavra("end");
        }
    }

    private void forwardingOn(Palavra p) {

        if (EnumType.values()[p.getType()] == EnumType.TipoI) {

            if (p.getOp().equals("sw")) {

                sw(p);
            }

        }

        if (EnumType.values()[p.getType()] == EnumType.TipoR) {
            if (!p.getPalavra().equals("noop") && !p.getPalavra().equals("end")) {

                R_Type(p);
            }

        }
        if (EnumType.values()[p.getType()] == EnumType.TipoI) {
            if (p.getOp().equals("lw")) {
                lw(p);
            }

        }

    }

    private void codeULA() {

        if (!decod_ula.getPalavra().equals("noop")) {

            switch (EnumType.values()[decod_ula.getType()]) {
                case TipoI:
                    // unlockRegisters(decod_ula);
                    I_Type(decod_ula);
                    if (Forwarding) {
                        forwardingOn(decod_ula);
                    }
                    break;
                case TipoJ:
                    J_Type(decod_ula);
                    break;
                case TipoR:
                    if (Forwarding) {
                        R_Type(decod_ula);
                    }
                    break;
                case TipoD:
                    D_Type(decod_ula);
                    break;

            }
            ula_memoria = decod_ula;
            //  System.out.println("ULA " + ula_memoria.getPalavra());
            decod_ula = new Palavra("noop");
        }

    }

    private void D_Type(Palavra p) {

        getTC c = new getTC(this, p.getTemp());
        c.setVisible(true);

    }

    private void memoria() {

        // lw ou sw
        if (EnumType.values()[ula_memoria.getType()] == EnumType.TipoI) {
            unlockRegisters(busca_decod);
            if (ula_memoria.getOp().equals("sw")) {

                sw(ula_memoria);
            }

        }

        memoria_writeback = ula_memoria;
        //  System.out.println("Memoria " + ula_memoria.getPalavra());
        ula_memoria = new Palavra("noop");

    }

    private void writeBack() {
        //add, sub,  mult ou div
        //  System.out.println("WriteBack: " + memoria_writeback.getPalavra());
        if (EnumType.values()[memoria_writeback.getType()] == EnumType.TipoR) {
            if (!memoria_writeback.getPalavra().equals("noop") && !memoria_writeback.getPalavra().equals("end")) {

                R_Type(memoria_writeback);
            }

        }
        if (EnumType.values()[memoria_writeback.getType()] == EnumType.TipoI) {
            if (memoria_writeback.getOp().equals("lw")) {
                lw(memoria_writeback);
            }

        }

        writeback = memoria_writeback;
        memoria_writeback = new Palavra("noop");

    }

    private void R_Type(Palavra p) {
        int valor;
        switch (p.getOp()) {
            case "add":
                valor = (registradores.get(p.getR2()).getValor() + registradores.get(p.getR3()).getValor());
                registradores.get(p.getR1()).setValor(valor);
                break;
            case "sub":
                valor = (registradores.get(p.getR2()).getValor() - registradores.get(p.getR3()).getValor());
                registradores.get(p.getR1()).setValor(valor);
                break;
            case "mult":
                valor = (registradores.get(p.getR2()).getValor() * registradores.get(p.getR3()).getValor());
                registradores.get(p.getR1()).setValor(valor);
                break;
            case "div":
                valor = (registradores.get(p.getR2()).getValor() / registradores.get(p.getR3()).getValor());
                registradores.get(p.getR1()).setValor(valor);
                break;
            case "noop":
                System.out.println("Noop Tipo R");
                break;

        }

//        String aux[] = p.getPalavra().split(",");
//        int r1 = Integer.parseInt(aux[1].substring(1, aux[1].length()));
//        int r2 = Integer.parseInt(aux[2].substring(1, aux[2].length()));
//        int r3 = Integer.parseInt(aux[3].substring(1, aux[3].length()));
//
//        if (aux[0].equals("add")) {
//            registradores.get(r1).setValor(registradores.get(r2).getValor() + registradores.get(r3).getValor());
//        }
//
//        if (aux[0].equals("sub")) {
//            registradores.get(r1).setValor(registradores.get(r2).getValor() - registradores.get(r3).getValor());
//        }
//
//        if (aux[0].equals("mult")) {
//            registradores.get(r1).setValor(registradores.get(r2).getValor() * registradores.get(r3).getValor());
//        }
//
//        if (aux[0].equals("div")) {
//            registradores.get(r1).setValor(registradores.get(r2).getValor() / registradores.get(r3).getValor());
//        }
    }

    private void I_Type(Palavra p) {
        switch (p.getOp()) {
            case "lw":
                p.setTemp(Integer.parseInt(Memoria[p.getOffset() + registradores.get(p.getR2()).getValor()]));
                break;
            case "sw":
                p.setTemp(registradores.get(p.getR2()).getValor());
                break;
            case "bne":
                unlockRegisters(p);
                if (registradores.get(p.getR1()).getValor() != registradores.get(p.getR2()).getValor()) {

                    PC = p.getOffset();
                    busca_decod = new Palavra("noop");
                    buscaOperando = new Palavra("noop");
                    jump = true;

                }

                break;
            case "beq":
                unlockRegisters(p);
                if (registradores.get(p.getR1()).getValor() == registradores.get(p.getR2()).getValor()) {
                    PC = p.getOffset();
                    busca_decod = new Palavra("noop");
                    buscaOperando = new Palavra("noop");
                    jump = true;
                }

                break;
            case "bltz":
                unlockRegisters(p);
                if (registradores.get(p.getR1()).getValor() < 0) {
                    PC = p.getOffset();
                    busca_decod = new Palavra("noop");
                    buscaOperando = new Palavra("noop");
                    jump = true;
                }

                break;
            case "bgtz":
                unlockRegisters(p);
                if (registradores.get(p.getR1()).getValor() > 0) {
                    PC = p.getOffset();
                    busca_decod = new Palavra("noop");
                    buscaOperando = new Palavra("noop");
                    jump = true;
                }
                break;
        }

    }

    private void J_Type(Palavra p) {
        jump = true;
        switch (p.getOp()) {
            case "j":
                PC = p.getOffset();
                busca_decod = new Palavra("noop");
                buscaOperando = new Palavra("noop");
                jump = true;

                break;
            case "jr":
                PC = registradores.get(p.getR1()).getValor();
                busca_decod = new Palavra("noop");
                buscaOperando = new Palavra("noop");
                jump = true;
                break;
        }

    }

    private void lw(Palavra p) {
        registradores.get(p.getR1()).setValor(p.getTemp());

    }

    private void sw(Palavra p) {
        Memoria[registradores.get(p.getR1()).getValor() + p.getOffset()] = String.valueOf(p.getTemp());

    }

//    private void R_TypeOperation(String aux[]) {
//        int primeiro = Integer.parseInt(aux[1].substring(1, aux[1].length() - 1));
//        int segundo = Integer.parseInt(aux[2].substring(1, aux[1].length() - 1));
//        int terceiro = Integer.parseInt(aux[3].substring(1, aux[1].length() - 1));
//
//        if (aux[0].equals("add")) {
//            System.out.println(Registradores[primeiro] + "=" + Registradores[segundo] + "+" + Registradores[terceiro]);
//
//            Registradores[primeiro] = String.valueOf(Integer.parseInt(Registradores[segundo]) + Integer.parseInt(Registradores[terceiro]));
//        }
//        if (aux[0].equals("sub")) {
//            System.out.println(Registradores[primeiro] + "=" + Registradores[segundo] + "-" + Registradores[terceiro]);
//            Registradores[primeiro] = String.valueOf(Integer.parseInt(Registradores[segundo]) - Integer.parseInt(Registradores[terceiro]));
//        }
//
//    }
//    private void I_TypeOperation(String aux[]) {
//
//        int s = Integer.parseInt(aux[1].substring(1, aux[1].length() - 1));
//        int t = Integer.parseInt(aux[2].substring(1, aux[2].length() - 1));
//
//        if (aux[0].equals("bne")) {
//            if (Registradores[s].equals(Registradores[t])) {
//                PC = Integer.parseInt(aux[3].substring(1, aux[1].length() - 1)) - 1;
//            }
//        } else {
//            if (aux[0].equals("bqe")) {
//                if (!Registradores[s].equals(Registradores[t])) {
//                    PC = Integer.parseInt(aux[3].substring(1, aux[1].length() - 1)) - 1;
//                }
//
//            } else {
//
//                if (aux[0].equals("bltz")) {
//
//                    if (Integer.parseInt(Registradores[Integer.parseInt(aux[1].substring(1, aux[1].length() - 1))]) > 0) {
//                        PC = Integer.parseInt(aux[2]) - 1;
//                    }
//
//                } else {
//                    if (aux[0].equals("blez")) {
//                        if (Integer.parseInt(Registradores[Integer.parseInt(aux[1].substring(1, aux[1].length() - 1))]) >= 0) {
//                            PC = Integer.parseInt(aux[2]) - 1;
//                        }
//                    } else {
//                        String Divide[] = aux[2].split("\\(");
//
//                        int PosicaoRegistrador = Integer.parseInt(aux[1].substring(1, aux[1].length() - 1));
//                        int PosicaoMemoria = Integer.parseInt(Divide[0]) + Integer.parseInt(Divide[1].substring(1, Divide[1].length() - 1));
//
//                        if (aux[0].equals("sw")) {
//                            Memoria[PosicaoMemoria] = Registradores[PosicaoRegistrador];
//                        } else {
//                            Registradores[PosicaoRegistrador] = Memoria[PosicaoMemoria];
//                        }
//                }
//
//            }
//        }
//    }
    private void R_TypeBinary(String aux[]) {

        String op = "000000";

        StringBuilder rt = new StringBuilder(Integer.toBinaryString(Integer.parseInt(aux[3].substring(1))));
        StringBuilder rd = new StringBuilder(Integer.toBinaryString(Integer.parseInt(aux[1].substring(1))));
        StringBuilder rs = new StringBuilder(Integer.toBinaryString(Integer.parseInt(aux[2].substring(1))));

        while (rt.length() < 5) {
            rt.insert(0, "0");
        }
        while (rd.length() < 5) {
            rd.insert(0, "0");
        }
        while (rs.length() < 5) {
            rs.insert(0, "0");
        }

//        
//        String rt = "ttttt";
//        String rd = "ddddd";
//        String rs = "sssss";
        String shamt = null;
        String funct = null;

        switch (aux[0]) {
            case "add":
                funct = "100000";
                break;
            case "sub":
                funct = "100010";
                break;
            case "mult":
                break;
            case "div":
                break;

        }

        shamt = "00000";

        StringBuilder Palavra = new StringBuilder(op);
        Palavra.append(rs);
        Palavra.append(rt);
        Palavra.append(rd);
        Palavra.append(shamt);
        Palavra.append(funct);

        InstrucoesBinarias.add(Palavra);
    }

    private void J_TypeBinary(String aux[]) {
        StringBuilder Palavra = new StringBuilder();
        String op = null;
        if (aux[0].equals("j")) {
            StringBuilder target = new StringBuilder(Integer.toBinaryString(Integer.parseInt(aux[1])));
            op = "000010";

            while (target.length() < 26) {
                target.insert(0, "0");
            }

            Palavra.append(op);
            Palavra.append(target);

        } else {
            op = "000000";
            StringBuilder registrador = new StringBuilder();

            registrador.append(Integer.toBinaryString(Integer.parseInt(aux[1].substring(1, aux[1].length()))));

            while (registrador.length() < 5) {

                registrador.insert(0, "0");
            }

            String fim = "000000000000000001000";

            Palavra.append(op);
            Palavra.append(registrador);
            Palavra.append(fim);

        }

        InstrucoesBinarias.add(Palavra);

    }

    private void J_TypeBinary2(String aux[]) {
        StringBuilder Palavra = new StringBuilder();
        String op = null;
        StringBuilder immediate = new StringBuilder();
        switch (aux[0]) {
            case "j":
                op = "000010";
                immediate.append(Integer.toBinaryString(Integer.parseInt(aux[1])));
                while (immediate.length() < 26) {
                    immediate.insert(0, "0");
                }
                Palavra.append(op);
                Palavra.append(immediate);

                break;
            case "jr":
                Palavra.append("00000");
                immediate.append(Integer.toBinaryString(Integer.parseInt(aux[1])));
                while (immediate.length() < 26) {
                    immediate.insert(0, "0");
                }
                Palavra.append(immediate);
                Palavra.append("000000000000000001000");
                break;

        }

        InstrucoesBinarias.add(Palavra);

    }

    private void I_TypeBinary(String aux[]) {
        StringBuilder Palavra = new StringBuilder();
        String Divide[] = aux[2].split("\\(");
        StringBuilder r1 = new StringBuilder();
        StringBuilder r2 = new StringBuilder();

        StringBuilder offset = new StringBuilder();
        String op = null;

        r1.append(Integer.toBinaryString(Integer.parseInt(aux[1].substring(1))));

        switch (aux[0]) {
            case "sw":
                op = "101011";
                offset.append(Integer.toBinaryString(Integer.parseInt(Divide[0])));
                r2.append(Integer.toBinaryString(Integer.parseInt(Divide[1].substring(1, Divide[1].length() - 1))));
                break;
            case "lw":
                op = "100011";
                offset.append(Integer.toBinaryString(Integer.parseInt(Divide[0])));
                r2.append(Integer.toBinaryString(Integer.parseInt(Divide[1].substring(1, Divide[1].length() - 1))));
                break;
            case "beq":
                op = "000100";
                r2.append(Integer.toBinaryString(Integer.parseInt(aux[2].substring(1))));
                offset.append(Integer.toBinaryString(Integer.parseInt(aux[3])));
                break;
            case "bne":
                op = "000101";
                r2.append(Integer.toBinaryString(Integer.parseInt(aux[2].substring(1))));
                offset.append(Integer.toBinaryString(Integer.parseInt(aux[3])));
                break;
            case "bltz":
                op = "000001";
                r2.append(Integer.toBinaryString(Integer.parseInt(aux[2].substring(1))));
                offset.append(Integer.toBinaryString(Integer.parseInt(aux[3])));
                break;
            case "bgtz":
                r2.append(Integer.toBinaryString(Integer.parseInt(aux[2].substring(1))));
                offset.append(Integer.toBinaryString(Integer.parseInt(aux[3])));
                op = "000111";
                break;
        }

////        StringBuilder rs = new StringBuilder(Integer.toBinaryString(Integer.parseInt(Divide[1].substring(1, Divide[1].length() - 1))));
////        StringBuilder rt = new StringBuilder(Integer.toBinaryString(Integer.parseInt(aux[1].substring(1, aux[1].length() - 1))));
////        StringBuilder immediate = new StringBuilder(Integer.toBinaryString(Integer.parseInt(Divide[0])));
        while (r1.length() < 5) {
            r1.insert(0, "0");
        }

        while (offset.length() < 16) {
            offset.insert(0, "0");

        }

        while (r2.length() < 5) {
            r2.insert(0, "0");
        }

        Palavra.append(op);

        if (aux[0].equals("sw") || aux[0].equals("lw")) {
            Palavra.append(r2);
            Palavra.append(r1);
        } else {
            Palavra.append(r1);
            Palavra.append(r2);
        }

        Palavra.append(offset);
        InstrucoesBinarias.add(Palavra);

    }

    private void D_TypeBinary(String aux[]) {
        StringBuilder Palavra = new StringBuilder();
        switch (aux[0]) {

            case "noop":
                Palavra.append("00000000000000000000000000000000");
                break;
            case "get_tc":
                Palavra.append("111111");
                StringBuilder memoria = new StringBuilder(Integer.toBinaryString(Integer.parseInt(aux[1])));
                while (memoria.length() < 10) {
                    memoria.insert(0, "0");
                }
                Palavra.append(memoria);
                Palavra.append("0000000000000000");
                break;

        }

        InstrucoesBinarias.add(Palavra);

    }

}
