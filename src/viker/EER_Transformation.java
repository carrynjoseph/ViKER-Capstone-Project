package viker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Represents the panel for a transformation from EER to ARM.
 * <p>
 * Contains the actual GUI elements and uses ARM_Model methods to draw onto the GUI
 * elements. Contains save button functionality.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class EER_Transformation extends JFrame {

    private VikerUI vkForm;
    private ARM_Model modelPanel;

    /**
     * Constructor for EER_Transformation GUI
     *
     * @param vk VikerUI form used to set up GUI
     */
    public EER_Transformation(VikerUI vk) {

        super("EER Transformation");
        vkForm = vk;
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panels for diagram and buttons
        modelPanel = new ARM_Model(this.vkForm);
        modelPanel.setBackground(Color.WHITE);
        //int numEntities = (this.vkForm.getT().getEntities().size() / 4) * 1000;
        int numEntities = 1500;
        modelPanel.setPreferredSize(new Dimension(numEntities, numEntities));
        JScrollPane scroll = new JScrollPane(modelPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.validate();
        scroll.setPreferredSize(new Dimension(numEntities, numEntities));
        //this.pack();
        this.add(scroll, BorderLayout.CENTER);
        scroll.createHorizontalScrollBar();
        scroll.createVerticalScrollBar();


        JPanel buttonPanel = new JPanel(new GridBagLayout());

        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        GridBagConstraints c = new GridBagConstraints();

        // Add buttons to interface
        JButton transformationLogButton = new JButton("Transformation Log");
        c.gridx = 500;
        //c.gridy = 500;
        c.insets = new Insets(5, 5, 5, 5);
        transformationLogButton.addActionListener(new transformationLogActionPerformed(this.vkForm));
        buttonPanel.add(transformationLogButton);
        JButton originalModel = new JButton("Transformation Status");
        c.gridx = 501;
        //c.gridy = 500;
        originalModel.addActionListener(new statusActionPerformed(this.vkForm));
        buttonPanel.add(originalModel);
        JButton newModelButton = new JButton("Input New Model");
        c.gridx = 502;
        //c.gridy = 500;
        newModelButton.addActionListener(new inputNewModelActionPerformed(this.vkForm, this));
        buttonPanel.add(newModelButton, c);
        JButton saveButton = new JButton("Save Model");
        c.gridx = 503;
        //c.gridy = 500;
        saveButton.addActionListener(new saveModelActionPerformed(this.modelPanel));
        buttonPanel.add(saveButton, c);
        JButton exitButton = new JButton("Exit");
        c.gridx = 504;
        //c.gridy = 500;
        exitButton.addActionListener(new exitActionPerformed());
        buttonPanel.add(exitButton, c);
       // repaint();
       pack();
    }

    /**
     * Class for the save button. Contains methods to save an EER Diagram.
     */
    static class saveModelActionPerformed implements ActionListener {

        public ARM_Model modelPanel;

        /**
         * Sets modelPanel
         *
         * @param modelPanel - EER_Model panel to save
         */
        public saveModelActionPerformed(ARM_Model modelPanel) {
            this.modelPanel = modelPanel;

        }

        /**
         * Action taken to draw the model to a savable file
         *
         * @param e - ActionEvent
         */
        public void actionPerformed(ActionEvent e) {
            BufferedImage imagebuf = new BufferedImage(modelPanel.getWidth(), modelPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = imagebuf.createGraphics();
            modelPanel.paint(graphics2D);
            try {
                String save = "ARM_Diagram";
                File file = new File(save + ".jpeg");
                while (file.exists()) {
                    save += "(1)";
                    file = new File(save + ".jpeg");
                }

                ImageIO.write(imagebuf, "jpeg", file);
            } catch (Exception ex) {
                // TODO Auto-generated catch block
                System.out.println("error");
            }
            final JFrame parent = new JFrame();

            JLabel textArea = new JLabel("File saved", SwingConstants.CENTER);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            parent.setLocation((int) (screenSize.getWidth() - 250) / 2, (int) (screenSize.getHeight() - 100) / 2);
            parent.setSize(new Dimension(250, 150));
            parent.add(textArea);
            Font labelFont = textArea.getFont();
            textArea.setFont(new Font(labelFont.getName(), Font.PLAIN, 25));
            parent.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            parent.setAlwaysOnTop(true);
            parent.setVisible(true);
        }


    }

    /**
     * Class to view the transformation log
     */
    static class transformationLogActionPerformed implements ActionListener {

        private VikerUI vkForm;
        private Transformation_Log log;

        /**
         * Constructor for transformationLogActionPerformed
         *
         * @param vk - VikerUI form
         */
        public transformationLogActionPerformed(VikerUI vk) {
            vkForm = vk;
            log = new Transformation_Log(vkForm);

        }

        public void actionPerformed(ActionEvent e) {
            log.setVisible(false);
            log = new Transformation_Log(vkForm);
            //log.getDetails().dispose();
            log.setLocation(300, 200);
            log.setVisible(true);
        }

    }

    /**
     * New model button to re-enter VikerGUI and input a new model
     */
    static class inputNewModelActionPerformed implements ActionListener {

        private VikerUI vkForm;
        private Transformation_Log log;
        private EER_Transformation frame;

        /**
         * Constructor for inputNewModelActionPerformed
         *
         * @param vk    - VikerUI form
         * @param frame - Frame to display/dispose
         */
        public inputNewModelActionPerformed(VikerUI vk, EER_Transformation frame) {
            vkForm = vk;
            this.frame = frame;
            log = new Transformation_Log(vk);


        }

        /**
         * Action taken to show the VikerUI
         *
         * @param e - ActionEvent
         */
        public void actionPerformed(ActionEvent e) {

            vkForm.setVisible(true);
            frame.dispose();
        }
    }
    
    static class statusActionPerformed implements ActionListener{
        
        private VikerUI vkForm;
        
        
        public statusActionPerformed(VikerUI vk){
            vkForm = vk;
            
        }
        
        public void actionPerformed(ActionEvent e){
            
            if (this.vkForm.getT().getStatus())
            {
                JOptionPane.showMessageDialog(null, "Transformation successful");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Some entities could not be transformed. Transformation Failed");
            }
            
              
        }
        
    }

    /**
     * Exit button performed
     */
    static class exitActionPerformed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
