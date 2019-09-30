package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import view.Principal_GUI;

public class SimuladorMIPS {

    public ArrayList<String> Instrucoes = new ArrayList<>();
    public ArrayList<StringBuilder> InstrucoesBinarias;
    public Processador XEON;
    public Principal_GUI principalgui = new Principal_GUI(this);

    public void LeituraDeArquivo() throws IOException {

        Instrucoes.clear();

        JFileChooser jfAbrir = new JFileChooser();
        jfAbrir.setCurrentDirectory(new File("."));
        int retorno = jfAbrir.showOpenDialog(null);
        String caminho = null;

        if (retorno == JFileChooser.APPROVE_OPTION) {
            caminho = jfAbrir.getSelectedFile().getAbsolutePath();

            FileInputStream arquivo = null;
            try {
                arquivo = new FileInputStream(caminho);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SimuladorMIPS.class.getName()).log(Level.SEVERE, null, ex);
            }

            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader ler = new BufferedReader(input);

            while (true) {
                String linha = ler.readLine();
                if (!(linha == null)) {
                    Instrucoes.add(linha);
                } else {
                    break;
                }
            }

            XEON = new Processador(Instrucoes, this);
            principalgui.setProcessador(XEON);

        } else {
            JOptionPane.showMessageDialog(null, "Sem entrada de arquivo válida.");

        }
    }

    public void ImprimeInstrucoes() {

        Instrucoes.forEach((i) -> {
            System.out.println(i);
        });

    }

    public void ImprimeInstrucoesBinarias() {

        InstrucoesBinarias.forEach((i) -> {
            System.out.println(i);
        });

    }

    public void GravaEmDisco() {
        InstrucoesBinarias = XEON.InstrucoesBinarias;

        Calendar c = Calendar.getInstance();
        String data = String.valueOf(c.get(Calendar.HOUR)) + "." + String.valueOf(c.get(Calendar.MINUTE)) + "." + String.valueOf(c.get(Calendar.DATE)) + "." + String.valueOf(c.get(Calendar.MONTH) +1);
    
 
        try {
            String pathFolder = "./src/Output/";
            String pathFile = data + ".fera";
            File file;

            file = new File(pathFolder + pathFile);
            file.getParentFile().mkdir();
            file.createNewFile();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                for (StringBuilder i : InstrucoesBinarias) {
                    bw.write(i.toString());
                    bw.write("\n");
                    bw.flush();
                }
            }
        } catch (IOException ex) {
        }

    }

}
