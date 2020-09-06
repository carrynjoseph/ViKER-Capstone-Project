package viker;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Represents the ARM_Model for a transformation from EER.
 * <p>
 * This contains methods used to draw the finished diagram on the screen. Final
 * layout has tables and connecting lines.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
class ARM_Model extends JPanel {

    private VikerUI vkForm;
    private final int XVal = 1100;
    private final int YVal = 630;
    private final int XStart = 10;
    private final int YStart = 60;

    private ArrayList<DrawnEntity> drawnEs;
    private int maxX;
    private int maxY;

    /**
     * Constructor for ARM_Model
     *
     * @param vk - VikerUI form for GUI purposes
     */
    public ARM_Model(VikerUI vk) {

        vkForm = vk;
        drawnEs = new ArrayList<>();
    }

    /**
     * Prints components on screen with connecting lines appropriately
     *
     * @param g - Graphics
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Font fontObject = new Font("SansSerif", Font.BOLD, 24);
        Font fontEntity = new Font("SansSerif", Font.PLAIN, 12);
        g.setFont(fontObject);
        g.setColor(Color.BLACK);
        String type = this.vkForm.getT().getType();
        g.drawString("ARM Diagram", 10, 50);
        g.setFont(fontEntity);
        int add = YStart;
        Font f = new Font("SansSerif", Font.PLAIN, 12);
        FontMetrics fm = g.getFontMetrics(f);
        g.setFont(f);
        for(String s: this.vkForm.getT().getARMModel()){
            g.drawString(s, XStart, add + fm.getHeight());
            add+= fm.getHeight();
        }
    }

    /**
     * Accessor for maximum x value the component can take on the panel
     *
     * @return - maximum x coordinate
     */
    public int maxX() {

        maxX = 0;

        for (DrawnEntity d : drawnEs) {
            if (d.getX() > maxX) {

                maxX = d.getX();
            }
        }

        return maxX;
    }

    /**
     * Accessor for maximum y value the component can take on the panel
     *
     * @return - maximum y coordinate
     */
    public int maxY() {

        maxY = 0;

        for (DrawnEntity d : drawnEs) {
            if (d.getY() > maxY) {

                maxY = d.getY();
            }
        }

        return maxY;
    }
}

