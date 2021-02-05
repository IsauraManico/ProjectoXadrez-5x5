
package chessgame;



import chess.Agente_IA;
import chess.PanelJogo;
import javax.swing.DefaultListModel;

public class Janela_Jogo extends javax.swing.JFrame
{

    PanelJogo gameScreen;
    
    /** 
     *cria anova janela do jogo
     */
    public Janela_Jogo() {
        initComponents();
        init();
        this.setSize(800, 800);
        //this.setResizable(false);
       // this.setLayout(null);
    }
   
    /**
     * Inicializa o jogo na form
     */
    private void init()
    {
        gameScreen = new PanelJogo(jPanel1.getWidth(), jPanel1.getHeight());
        jPanel1.add(gameScreen);
    }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar_Main = new javax.swing.JMenuBar();
        jMenu_Game = new javax.swing.JMenu();
        jMenuItem_New1P = new javax.swing.JMenuItem();
        jMenuItem_New2P = new javax.swing.JMenuItem();
        jMenuItem_Undo = new javax.swing.JMenuItem();
        jMenuItem_Close = new javax.swing.JMenuItem();
        jMenu_File = new javax.swing.JMenu();
        jMenuItem_Save = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Xadrez"); // NOI18N
        setLocationByPlatform(true);

        jPanel1.setMaximumSize(new java.awt.Dimension(10000, 10000));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1_componentResized(evt);
            }
        });

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jList2AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(jList2);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jList1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("WHITE");

        jLabel2.setText("BLACK");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(568, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(151, 151, 151))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(148, 148, 148))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 358, Short.MAX_VALUE))
        );

        jMenu_Game.setText("Jogo");

        jMenuItem_New1P.setText("Novo 1-Player");
        jMenuItem_New1P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_New1PActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_New1P);

        jMenuItem_New2P.setText("Novo 2-Player");
        jMenuItem_New2P.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_New2PActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_New2P);

        jMenuItem_Undo.setText("Recomecar");
        jMenuItem_Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_UndoActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_Undo);

        jMenuItem_Close.setText("Fechar");
        jMenuItem_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_CloseActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_Close);

        jMenuBar_Main.add(jMenu_Game);

        jMenu_File.setText("Arquivo");

        jMenuItem_Save.setText("Sobre");
        jMenuItem_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_SaveActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_Save);

        jMenuItem1.setText("Salvar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem1);

        jMenuBar_Main.add(jMenu_File);

        setJMenuBar(jMenuBar_Main);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
private void jMenuItem_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_CloseActionPerformed
    this.dispose();
}//GEN-LAST:event_jMenuItem_CloseActionPerformed

private void jMenuItem_New2PActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_New2PActionPerformed
    gameScreen.novoJogo();
}//GEN-LAST:event_jMenuItem_New2PActionPerformed

private void jMenuItem_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_SaveActionPerformed
  //  gameScreen.saveBoard();
}//GEN-LAST:event_jMenuItem_SaveActionPerformed

private void jMenuItem_UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_UndoActionPerformed
    gameScreen.desfazer();
}//GEN-LAST:event_jMenuItem_UndoActionPerformed

private void jMenuItem_New1PActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_New1PActionPerformed
    gameScreen.novoIAJogo();
}//GEN-LAST:event_jMenuItem_New1PActionPerformed

private void jPanel1_componentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1_componentResized
    // allow the game board to be resized
    if (jPanel1 != null && gameScreen != null) {
        int smallerDimension = jPanel1.getHeight();
        if (jPanel1.getWidth() < smallerDimension)
            smallerDimension = jPanel1.getWidth();
        // make sure the board stays square and completely visible
        gameScreen.setSize(smallerDimension, smallerDimension);
    }
}//GEN-LAST:event_jPanel1_componentResized

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

            gameScreen.saveBoard();

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jList2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jList2AncestorAdded
            DefaultListModel<String>l1 = new DefaultListModel<>();  
           
                  
            jList2.setModel(l1);
            jList2.setModel(gameScreen.l1);
        // TODO add your handling code here:
    }//GEN-LAST:event_jList2AncestorAdded

    private void jList1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jList1AncestorAdded

                
        this.jList1.setModel(Agente_IA.l1);
        
    }//GEN-LAST:event_jList1AncestorAdded

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Janela_Jogo().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JMenuBar jMenuBar_Main;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem_Close;
    private javax.swing.JMenuItem jMenuItem_New1P;
    private javax.swing.JMenuItem jMenuItem_New2P;
    private javax.swing.JMenuItem jMenuItem_Save;
    private javax.swing.JMenuItem jMenuItem_Undo;
    private javax.swing.JMenu jMenu_File;
    private javax.swing.JMenu jMenu_Game;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}
