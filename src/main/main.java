package main;



import controller.MipsSimulator;

import java.util.ArrayList;

public class main {

    public static void main(String[] args) {

        ArrayList<String> Instrucoes = new ArrayList<>();
        
//        Instrucoes.add("add $0, $1, $2 ");
//        Instrucoes.add("sub $0, $0, $1 ");
//        Instrucoes.add("lw $0, 10($2)");
//        Instrucoes.add("j 50");
//        Instrucoes.add("jr $5");
//        Processador XEON = new Processador(Instrucoes);
//        XEON.LeituraInstrucoes(Instrucoes);
//        XEON.ImprimeInstrucoes();
//        XEON.GravaEmDisco();

        MipsSimulator mips = new MipsSimulator();
        

    }

}
