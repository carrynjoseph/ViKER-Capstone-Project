package viker;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a DrawnEntity object
 * <p>
 * This contains information about each entity that is used to display it on the screen.
 * It uses an x,y coordinate system to determine where to place the entity on the screen.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class DrawnEntity {

    private int x;
    private int y;
    private Entity e;
    private int[][] dimensions;

    /**
     * Constructs a drawn entity - an entity to be drawn on screen
     *
     * @param entity      - Entity to draw
     * @param xCoordinate - x position to draw at
     * @param yCoordinate - y position to draw at
     */
    public DrawnEntity(Entity entity, int xCoordinate, int yCoordinate) {

        e = entity;
        x = xCoordinate;
        y = yCoordinate;
    }

    /**
     * Get the rectangle size needed to draw the Entity
     *
     * @param g - Graphics
     * @param f - Font
     * @return - 2D array of output rectangle
     */
    public int[][] getRectangleSize(Graphics g, Font f, String type) {

        if (type.contains("EER")) {
            int width = 0;

            int height = 0;
            Attributes a = e.getAttributes();
            ArrayList<String> pList = a.getPrimaryKeys();
            ArrayList<String> oList = a.getOtherAttributes();
            ArrayList<String> connectionList = e.getConnections();
            int eNameWidth = this.checkPixelWidth(e.getEntityName(), f, g);

            if (eNameWidth > width) {
                width = eNameWidth + 2;
            }

            int self = this.checkPixelWidth("self*", f, g);

            if (self > width) {
                width = self;
            }
            if (e.getAttributes() != null) {
                if (pList.size() > 0) {
                    for (String s : pList) {
                        int pWidth = this.checkPixelWidth(s, f, g);
                        if (pWidth > width) {
                            width = pWidth;
                        }
                    }
                }
                if (oList.size() > 0) {
                    for (String s : oList) {
                        int oWidth = this.checkPixelWidth(s, f, g);
                        if (oWidth > width) {
                            width = oWidth;
                        }
                    }
                }
                if (connectionList.size() > 0) {
                    for (String s : connectionList) {
                        int cWidth = this.checkPixelWidth(s + "*", f, g);
                        if (cWidth > width) {
                            width = cWidth;
                        }
                    }
                }
            }

            FontMetrics fm = g.getFontMetrics(f);
            int totalAttributes = pList.size() + oList.size() + connectionList.size() + 2;
            height = (totalAttributes * fm.getHeight()) + (totalAttributes * 5);

            int[][] xy = new int[1][2];
            xy[0][0] = width;
            xy[0][1] = height;
            dimensions = xy;

            return xy;
        } else if (type.contains("ARM")) {
            int width = this.checkPixelWidth(e.getEntityName(), f, g) + 20;
            int height = g.getFontMetrics().getHeight() + 20;
            int[][] xy = new int[1][2];
            xy[0][0] = width;
            xy[0][1] = height;
            dimensions = xy;

            return xy;
        }

        return null;
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

    /**
     * Accessor for dimensions array
     *
     * @return - dimensions array
     */
    public int[][] getDimensions() {
        return dimensions;
    }
}
