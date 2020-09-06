package viker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Represents a Component object
 * <p>
 * Represents a Component for which relevant information has been extracted from an XML file.
 * Stores identifiers, values, and types. Contains some information about parent element.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class Component {

    private String id;
    private String value;
    private String type;
    private String parentID;

    /**
     * Constructs a Component object with unique ID
     *
     * @param id    - unique identifier for a Component
     * @param value - Information about the Component
     * @param type  - Type of Component (Relationship, Entity, Attribute, Connecting Lines, etc.)
     */
    public Component(String id, String value, String type) {

        this.id = id;
        this.value = value;
        this.type = type;

        if (type.equals("attributes")) {
            parentID = "";
        }
    }

    /**
     * Sets ID of current element's parent
     */
    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    /**
     * Accessor ID of parent element
     *
     * @return parent ID
     */
    public String getParentID() {
        return parentID;
    }

    /**
     * Accessor for identifier.
     *
     * @return <var>id</var> of Component
     */
    public String getId() {
        return id;
    }

    /**
     * Accessor for value (information).
     *
     * @return <var>value</var> of Component
     */
    public String getValue() {
        return value;
    }

    /**
     * Accessor for type.
     *
     * @return <var>type</var> of Component
     */
    public String getType() {
        return type;
    }
}

