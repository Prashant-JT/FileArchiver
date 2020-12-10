package com.mycompany.filearchiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class MainFrame extends javax.swing.JFrame {

    DefaultListModel fileModel = new DefaultListModel();
    JFileChooser fc = new JFileChooser();
    List<File> saveFiles = new LinkedList<>();
    ProgressBar progressBarClass = new ProgressBar();
    File selectedFolder;
    String savePath = "";
    
    private class ProgressBar extends SwingWorker<Void, Void> {
        private int progress = 0;
        
        @Override
        protected Void doInBackground() throws Exception {
            List<File> selectedFiles = new LinkedList<>();
            int[] selectedIndices = listFiles.getSelectedIndices();
            
            for (int selected : selectedIndices) {
                selectedFiles.add(saveFiles.get(selected));
            }
            
            try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(savePath + ".zip"); // path al guardar
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte[] data = new byte[1024];
            Iterator i = saveFiles.iterator();
            
            int c = 0;
            while (i.hasNext() && !this.isCancelled()) {
                c++;                
                File filename = (File) i.next();
                String path = filename.getPath();
                FileInputStream fi = new FileInputStream(path);
                origin = new BufferedInputStream(fi, 1024);
                ZipEntry entry = new ZipEntry(filename.getName());
                out.putNextEntry(entry);
                
                int count;
                while ((count = origin.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, count);
                }
                progressBar.setValue(((c * 100) / saveFiles.size()));
                origin.close();
            }
            out.close();
            }catch( IOException e){}
            
            return null;
        }
        
        @Override
        public void done(){
            if (this.isCancelled()) {
                JOptionPane.showMessageDialog(rootPane, "Compression have been canceled", 
                            "Compression canceled", JOptionPane.INFORMATION_MESSAGE);
                
                progressBar.setValue(0);
                infoLabel.setText("");
                infoLabelOpen.setText("Choose a folder to open");
                cancelButton.setEnabled(false);
                return;
            }
            
            infoLabel.setText("Done!");
            fileModel.clear();
            JOptionPane.showMessageDialog(rootPane, "Your files have been compressed!", 
                        "Files compressed!", JOptionPane.INFORMATION_MESSAGE);
            infoLabelOpen.setText("Choose a folder to open");
            cancelButton.setEnabled(false);
        }
        
    }
    
    public MainFrame() {
        initComponents();
        listFiles.setModel(fileModel);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        progressBar.setStringPainted(true);
        cancelButton.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listFiles = new javax.swing.JList<>();
        progressBar = new javax.swing.JProgressBar();
        infoLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        infoLabelOpen = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        openOption = new javax.swing.JMenuItem();
        jSeparator = new javax.swing.JPopupMenu.Separator();
        exitOption = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        zipOption = new javax.swing.JMenuItem();
        aboutMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listFiles.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(listFiles);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        infoLabelOpen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabelOpen.setText("Choose a folder to open");

        FileMenu.setText("File");

        openOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openOption.setText("Open Folder");
        openOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openOptionActionPerformed(evt);
            }
        });
        FileMenu.add(openOption);
        FileMenu.add(jSeparator);

        exitOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        exitOption.setText("Exit");
        exitOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitOptionActionPerformed(evt);
            }
        });
        FileMenu.add(exitOption);

        jMenuBar1.add(FileMenu);

        editMenu.setText("Edit");

        zipOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        zipOption.setText("Compress Files");
        zipOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zipOptionActionPerformed(evt);
            }
        });
        editMenu.add(zipOption);

        jMenuBar1.add(editMenu);

        aboutMenu.setText("About");

        jMenuItem1.setText("Authors");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        aboutMenu.add(jMenuItem1);

        jMenuBar1.add(aboutMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoLabelOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(infoLabelOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void zipOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zipOptionActionPerformed
        if (listFiles.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(null, "You have not selected any files to compress!", 
                    "Files not selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser fcSave = new JFileChooser();
        int res = fcSave.showSaveDialog(this);
        
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = fcSave.getSelectedFile();
            int res2 = JOptionPane.showConfirmDialog(rootPane, "Are you sure?", 
                    "Save selected files to compress", JOptionPane.YES_NO_OPTION);
            
            if (res2 == JOptionPane.YES_OPTION) {
                cancelButton.setEnabled(true);
                savePath = file.getAbsolutePath();
                infoLabel.setText("Compressing files ...");
                progressBarClass.execute();
            }
        }
        
    }//GEN-LAST:event_zipOptionActionPerformed

    private void openOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openOptionActionPerformed
        saveFiles.clear();
        fileModel.clear();
        
        int res = fc.showOpenDialog(null);
        
        if(res == JFileChooser.APPROVE_OPTION) {
            selectedFolder = fc.getSelectedFile();
            File[] listFiles = selectedFolder.listFiles();
            for (File file : listFiles) {
                if (file.isFile()) {
                    this.saveFiles.add(file);
                    this.fileModel.addElement(file.getName());
                }
            }
            infoLabelOpen.setText("Select files to compress from '" 
                    + selectedFolder.getName() + "'");
        }
    }//GEN-LAST:event_openOptionActionPerformed

    private void exitOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitOptionActionPerformed
        int res = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        
        if(res == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_exitOptionActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Luis Galindo Molina | Prashant Jeswani Tejwani", 
                "Authors", WIDTH);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        progressBarClass.cancel(true);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

}
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setResizable(false);
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JButton cancelButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitOption;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel infoLabelOpen;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator;
    private javax.swing.JList<String> listFiles;
    private javax.swing.JMenuItem openOption;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem zipOption;
    // End of variables declaration//GEN-END:variables
}
