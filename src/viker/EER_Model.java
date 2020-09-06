package viker;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Represents the EER_Model for a transformation from ARM.
 * <p>
 * This contains methods used to draw the finished diagram on the screen. Final
 * layout has different shapes for each element (relationship, attributes, entity) and
 * contains connecting lines between them where appropriate.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class EER_Model extends JPanel {

    private VikerUI vkForm;
    private final int XStart = 10;
    private final int YStart = 60;
    private ArrayList<DrawnRelationship> dRelations;

    /**
     * Constructor for the EER_Model
     *
     * @param vk - VikerUI form for GUI purposes
     */
    public EER_Model(VikerUI vk) {

        vkForm = vk;
        dRelations = new ArrayList<>();
    }

    /**
     * Paints the diagram components on the screen
     *
     * @param g - Graphics object to use
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font fontObject = new Font("SansSerif", Font.BOLD, 24);
        Font fontEntity = new Font("SansSerif", Font.PLAIN, 12);
        Font fontPrimaryKey = new Font("SansSerif", Font.BOLD, 12);
        
        g.setFont(fontObject);
        g.setColor(Color.BLACK);
        g.drawString("ERR Diagram", 10, 50);
        g.setFont(fontEntity);
        //private javax.swing.JTextField.model_textARea;
        ArrayList<String> output = this.vkForm.getT().getEERModel();
        int add = YStart;
        Font f = new Font("SansSerif", Font.PLAIN, 12);
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(f);
        for(String s: output){

            g.drawString(s, XStart, add + fm.getHeight());
            add+= fm.getHeight();
        }
        
          
        
    }

    /**
     * Determine the pixel width needed to house the text
     *
     * @param s - String to print
     * @param f - Font to use for String
     * @param g - Graphics to use
     * @return - pixel width as integer
     */
    public int checkPixelWidth(String s, Font f, Graphics g) {

        FontMetrics fm = g.getFontMetrics(f);
        int w = 0;

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);
            w += fm.charWidth(c) + 2;
        }

        return w;
    }
}
