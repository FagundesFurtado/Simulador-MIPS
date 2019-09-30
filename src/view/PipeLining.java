package view;

import controller.Processador;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class PipeLining extends javax.swing.JFrame {

    public Processador XEON;

    public PipeLining(Processador pc) {

        initComponents();
        this.XEON = pc;
        setVisible(false);
        registradores.setLineWrap(true);

        setIconImage(new ImageIcon("./image/logo.jpg").getImage());

        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Pane = new javax.swing.JPanel();
        Forwarding = new javax.swing.JToggleButton();
        Next = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        busca = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        decod = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        ula = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        memoria = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        writeback = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Clock = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        PC = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        forwarding = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        registradores = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        btnBinario = new javax.swing.JButton();
        Fechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulador MIPS");
        setUndecorated(true);

        Pane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Forwarding.setText("Forwarding");
        Forwarding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForwardingActionPerformed(evt);
            }
        });

        Next.setText("Próximo");
        Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextActionPerformed(evt);
            }
        });

        busca.setColumns(20);
        busca.setRows(5);
        jScrollPane1.setViewportView(busca);

        decod.setColumns(20);
        decod.setRows(5);
        jScrollPane2.setViewportView(decod);

        ula.setColumns(20);
        ula.setRows(5);
        jScrollPane3.setViewportView(ula);

        memoria.setColumns(20);
        memoria.setRows(5);
        jScrollPane4.setViewportView(memoria);

        writeback.setColumns(20);
        writeback.setRows(5);
        jScrollPane5.setViewportView(writeback);

        jLabel1.setText("BUSCA DE OPERANDO");

        jLabel2.setText("DECODIFICACAO DE OPERANDOS");

        jLabel3.setText("                     ULA");

        jLabel4.setText("MEMORIA");

        jLabel5.setText("WRITE BACK");

        jLabel6.setText("Clock:");

        jLabel7.setText("PC:");

        PC.setText(" ");

        jLabel8.setText("FORWARDING: ");

        forwarding.setText("  ");

        registradores.setColumns(20);
        registradores.setRows(5);
        jScrollPane6.setViewportView(registradores);

        jLabel9.setText("REGISTRADORES");

        btnBinario.setText("Gerar Binário");
        btnBinario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBinarioActionPerformed(evt);
            }
        });

        Fechar.setText("X");
        Fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PaneLayout = new javax.swing.GroupLayout(Pane);
        Pane.setLayout(PaneLayout);
        PaneLayout.setHorizontalGroup(
            PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneLayout.createSequentialGroup()
                .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBinario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Forwarding)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Next))
                    .addGroup(PaneLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PaneLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PaneLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(31, 31, 31)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PaneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneLayout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(jLabel5)
                                .addGap(24, 24, 24)))
                        .addGap(0, 40, Short.MAX_VALUE))
                    .addGroup(PaneLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Clock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PC, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(forwarding, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(306, 306, 306)
                        .addComponent(Fechar))
                    .addGroup(PaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6)))
                .addContainerGap())
            .addGroup(PaneLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PaneLayout.setVerticalGroup(
            PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PaneLayout.createSequentialGroup()
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(forwarding))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(PaneLayout.createSequentialGroup()
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PaneLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(Clock, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(PC)))
                            .addComponent(Fechar))
                        .addGap(107, 107, 107)
                        .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))))
                .addGap(18, 18, 18)
                .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Forwarding)
                    .addComponent(Next)
                    .addComponent(btnBinario))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ForwardingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForwardingActionPerformed
        XEON.Forwarding = !(XEON.Forwarding);


    }//GEN-LAST:event_ForwardingActionPerformed

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextActionPerformed
        ArrayList<String> aux = XEON.NextOperation();

        if (aux.size() > 0) {
            busca.setText(aux.get(0));
            decod.setText(aux.get(1));
            ula.setText(aux.get(2));
            memoria.setText(aux.get(3));
            writeback.setText(aux.get(4));
            PC.setText(aux.get(5));
            Clock.setText(aux.get(6));
            forwarding.setText(aux.get(7));
            registradores.setText(aux.get(8));

        }

        // XEON.leituraDeInstrucao();
    }//GEN-LAST:event_NextActionPerformed

    private void btnBinarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBinarioActionPerformed
        XEON.geraBinario();


    }//GEN-LAST:event_btnBinarioActionPerformed

    private void FecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FecharActionPerformed
        System.exit(0);
    }//GEN-LAST:event_FecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Clock;
    private javax.swing.JButton Fechar;
    private javax.swing.JToggleButton Forwarding;
    private javax.swing.JButton Next;
    private javax.swing.JLabel PC;
    private javax.swing.JPanel Pane;
    private javax.swing.JButton btnBinario;
    private javax.swing.JTextArea busca;
    private javax.swing.JTextArea decod;
    private javax.swing.JLabel forwarding;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea memoria;
    private javax.swing.JTextArea registradores;
    private javax.swing.JTextArea ula;
    private javax.swing.JTextArea writeback;
    // End of variables declaration//GEN-END:variables
}
