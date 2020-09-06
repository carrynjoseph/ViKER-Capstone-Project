package viker;

/**
 * Represents a DrawnRelationship object
 * <p>
 * Contains details on where to draw a relationship on the panel.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class DrawnRelationship {

    private int[][] corners;
    private final Relationship r;

    /**
     * Constructor for DrawnRelationship
     *
     * @param r       - relationship to draw
     * @param corners - corners of the shape for the represented relationship
     */
    public DrawnRelationship(Relationship r, int[][] corners) {

        this.r = r;
        this.corners = corners;
    }

    /**
     * Accessor for corners array of relationship shape
     *
     * @return - 2D array of corners
     */
    public int[][] getCorners() {

        return corners;
    }

    /**
     * Access the relationship associated with the DrawnRelationship
     *
     * @return - relationship
     */
    public Relationship getR() {

        return r;
    }
}
