package viker;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * Represents the interface for reading files and selecting transform.
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class VikerUI extends javax.swing.JFrame {

    /**
     * Creates new form VikerUI
     */
    private static ArrayList<String> inputModel = new ArrayList<String>();
    ;
    private static String fileName;
    private Transformation t;

    public VikerUI() {
        super("ViKER Transformstion System");
        initComponents();
        t = new Transformation();
        transform_button.setEnabled(false);
        fileName = "";
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        input_textbox = new javax.swing.JTextField();
        transform_button = new javax.swing.JButton();
        find_button = new javax.swing.JButton();
        input_button = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        Exit_button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        input_textbox.setText("e.g. c:\\Dekstop\\model.xml");

        transform_button.setText("Transform");
        transform_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transform_buttonActionPerformed(evt);
            }
        });

        find_button.setText("Find file");
        find_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                find_buttonActionPerformed(evt);
            }
        });

        input_button.setText("Input file");
        input_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_buttonActionPerformed(evt);
            }
        });

        title.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        title.setText("Welcome to ViKer Transformation System");

        Exit_button.setText("Exit");
        Exit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Exit_buttonActionPerformed(evt);
            }
        });

        jLabel1.setText("Please insert a .xml file formatted by Draw.io");

        jLabel2.setText("ViKER Transformation System is a knowledge-based data access tool that transforms an EER to/from an ARM");

        jLabel3.setText("diagram. The system allows for transformation of models, provides a log of transformed entities and the ability");

        jLabel4.setText("to save transformed models.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(transform_button, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(593, 593, 593)
                        .addComponent(Exit_button, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(input_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(find_button, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(input_button, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(jLabel4)
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(input_textbox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(find_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(transform_button, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(Exit_button))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void transform_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transform_buttonActionPerformed

        t.transformModel();
        this.setVisible(false);
        if (t.getType().contains("EER")) {

            EER_Transformation model = new EER_Transformation(this);
            model.setLocationRelativeTo(null);
            model.setVisible(true);
        } else if (t.getType().contains("ARM")) {
            ARM_Transformation model = new ARM_Transformation(this);
            model.setLocationRelativeTo(null);
            model.setVisible(true);
        }

        transform_button.setEnabled(false);


    }//GEN-LAST:event_transform_buttonActionPerformed

    private void find_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_find_buttonActionPerformed
        // Code to find file path on computer hardrive
        final JFileChooser fc = new JFileChooser();
        int response = fc.showOpenDialog(VikerUI.this);
        if (response == JFileChooser.APPROVE_OPTION) {
            String str = fc.getSelectedFile().toString();
            input_textbox.setText(str);
            fileName = str;
        }

    }//GEN-LAST:event_find_buttonActionPerformed

    private void input_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_buttonActionPerformed
        //reads file and stores file information(model) in an array

        String input = input_textbox.getText().replace("\\", "\\\\"); //.replace() needed to fix file path format
        if (!input.contains(".xml")) {
            JOptionPane.showMessageDialog(null, "File inputed is not an xml file. Please input an xml file.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            fileName = input;
            try {
                File file = new File(fileName);
                t.storeContents(fileName);
                //System.out.println("read successfully");
                transform_button.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error reading file. Try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_input_buttonActionPerformed

    private void Exit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Exit_buttonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_Exit_buttonActionPerformed

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
            java.util.logging.Logger.getLogger(VikerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VikerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VikerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VikerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VikerUI vk = new VikerUI();
                vk.setLocationRelativeTo(null); //displays frame in centre of screen
                vk.setVisible(true);


            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Exit_button;
    private javax.swing.JButton find_button;
    private javax.swing.JButton input_button;
    private javax.swing.JTextField input_textbox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel title;
    public javax.swing.JButton transform_button;
    // End of variables declaration//GEN-END:variables

    //Accessor methods
    public static String getFileName() {
        return fileName;
    }

    /**
     * Accessor for the Transformation (one active when program runs)
     *
     * @return - <var>transformation</var>
     */
    public Transformation getT() {
        return t;
    }


}
