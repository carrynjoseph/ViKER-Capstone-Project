package viker;

import java.util.ArrayList;

/**
 * Represents Attributes
 * <p>
 * Contains the lists for primary keys, other attributes, and foreign keys.
 * Each Entity usually has only one Attributes object.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class Attributes {

    private ArrayList<String> primaryKeys;
    private ArrayList<String> otherAttributes;
    private ArrayList<String> foreignKeys;
    private String[] info1;

    /**
     * Attributes constructor
     *
     * @param type - Type of entity that is created (weak/strong)
     * @param info - all relevant info about an entity's attributes
     */
    public Attributes(String type, String[] info) {

        this.info1 = info;

        if (type.equals("strong")) {
            primaryKeys = new ArrayList<>();
            otherAttributes = new ArrayList<>();
        } else {
            primaryKeys = new ArrayList<>();
            otherAttributes = new ArrayList<>();
            foreignKeys = new ArrayList<>();
        }
    }

    /**
     * Orders the attributes from <var>info</var> into separate lists - strong/weak/other.
     */
    public void orderAttributes() {

        for (int i = 0; i < info1.length; i++) {
            if (info1[i].startsWith("^")) {
                primaryKeys.add(info1[i].substring(1));
            } else {
                otherAttributes.add(info1[i]);
            }
        }
    }

    /**
     * Set <var>foreignKeys</var> ArrayList to ArrayList that's inputted.
     *
     * @param arr - ArrayList to add foreign key to
     */
    public void addForeignKey(ArrayList<String> arr) {

        foreignKeys = arr;
    }

    /**
     * Accessor for <var>primaryKeys</var> ArrayList.
     *
     * @return - ArrayList of primary keys
     */
    public ArrayList<String> getPrimaryKeys() {
        return primaryKeys;
    }

    /**
     * Accessor for <var>otherAttributes</var> ArrayList.
     *
     * @return - ArrayList of other attributes keys
     */
    public ArrayList<String> getOtherAttributes() {
        return otherAttributes;
    }

    /**
     * Accessor for <var>foreignKeys</var> ArrayList.
     *
     * @return - ArrayList of foreign keys
     */
    public ArrayList<String> getForeignKeys() {
        return foreignKeys;
    }
}
    
    

