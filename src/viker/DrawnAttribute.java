package viker;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Represents a DrawnAttribute object
 * <p>
 * This contains information about each attribute that is used to display it on the screen.
 * It uses an x,y coordinate system to determine where to place the attribute on the panel.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class DrawnAttribute {

    private Entity e;
    private String name;
    private int[][] dim;

    private int x;
    private int y;

    /**
     * Constructor for a DrawnAttribute - used to draw attributes for final diagram
     *
     * @param e    - Entity it's attached to
     * @param name - name of attribute
     * @param x    - x coordinate
     * @param y    - y coordinate
     */
    public DrawnAttribute(Entity e, String name, int x, int y) {

        this.e = e;
        this.name = name;
        this.x = x;
        this.y = y;

    }

    /**
     * Get the oval size and dimensions needed for an attribute to fit
     *
     * @param g - graphics to draw on
     * @param f - font to use
     * @return - 2D array of dimensions representing an oval
     */
    public int[][] getOvalSize(Graphics g, Font f) {

        int width = this.checkPixelWidth(name, f, g) + 10;

        if (width % 2 != 0) {
            width++;
        }

        int height = g.getFontMetrics().getHeight() + 10;

        if (height % 2 != 0) {
            height++;
        }

        dim = new int[1][2];
        dim[0][0] = width;
        dim[0][1] = height;
        return dim;
    }

    /**
     * Check the pixel width of a String
     *
     * @param s - String to check
     * @param f - Font of output
     * @param g - Graphics to draw on
     * @return - pixel width of String as an integer
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

    /**
     * Accessor for x coordinate
     *
     * @return - x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Accessor for y coordinate
     *
     * @return - y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Accessor for entity associated with these coordinates
     *
     * @return - Entity
     */
    public Entity getE() {
        return e;
    }
}
