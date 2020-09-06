package viker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 * Represents the panel for a transformation from ARM to EER.
 * <p>
 * Contains the actual GUI elements and uses EER_Model methods to draw onto the GUI
 * elements. Contains save button functionality.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class ARM_Transformation extends JFrame {

    private VikerUI vkForm;
    private EER_Model modelPanel;

    /**
     * Constructor for ARM_Transformation GUI
     *
     * @param vk VikerUI form used to set up GUI
     */
    public ARM_Transformation(VikerUI vk) {

        super("ARM Transformation");
        vkForm = vk;
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panels for diagram and buttons
        modelPanel = new EER_Model(this.vkForm);
        modelPanel.setBackground(Color.WHITE);
        //int numEntities = (this.vkForm.getT().getEntities().size() / 4) * 1000;
        int numEntities = 1500;
        modelPanel.setPreferredSize(new Dimension(numEntities, numEntities));
        JScrollPane scroll = new JScrollPane(modelPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.validate();
        scroll.setPreferredSize(new Dimension(numEntities, numEntities));
        this.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        //buttonPanel.setSize(1000, 200);
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


        public EER_Model modelPanel;

        /**
         * Sets modelPanel
         *
         * @param modelPanel - EER_Model panel to save
         */
        public saveModelActionPerformed(EER_Model modelPanel) {

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
            modelPanel.paintComponent(graphics2D);

            // Saves file and ensures filename uniqueness
            try {
                String save = "EER_Diagram";
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
            log.setLocation(300, 200);
            log.setVisible(true);
        }
    }
    
    /**
     * Transformation status button displays success or failure of transformation
     */
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
     * New model button to re-enter VikerGUI and input a new model
     */
    static class inputNewModelActionPerformed implements ActionListener {

        private VikerUI vkForm;
        private Transformation_Log log;
        private ARM_Transformation frame;

        /**
         * Constructor for inputNewModelActionPerformed
         *
         * @param vk    - VikerUI form
         * @param frame - Frame to display/dispose
         */
        public inputNewModelActionPerformed(VikerUI vk, ARM_Transformation frame) {

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

    /**
     * Exit button performed
     */
    static class exitActionPerformed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
