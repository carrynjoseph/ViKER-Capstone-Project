package viker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Represents a Connection object
 * <p>
 * A connection is formed between two pieces of a diagram - attributes, relationships, and entities.
 * The connections are stored using source and destination IDs and also contain the type of connection and
 * cardinality of a connection.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class Connection {

    private String sourceID;
    private String targetID;
    private String type;
    private String cardinality;

    /**
     * Constructor of Connection
     *
     * @param source - source element
     * @param target - target element
     * @param type   - type of connection
     */
    public Connection(String source, String target, String type) {

        sourceID = source;
        targetID = target;
        this.type = type;

        if (type.equalsIgnoreCase("relationship")) {
            cardinality = "";
        }
    }

    /**
     * Set the cardinality for a relationship.
     *
     * @param relation
     */
    public void setCardinality(String relation) {

        cardinality = relation;
    }

    /**
     * Accessor for source element ID.
     *
     * @return - sourceID (String)
     */
    public String getSourceID() {
        return sourceID;
    }

    /**
     * Accessor for target element ID.
     *
     * @return - targetID (String)
     */
    public String getTargetID() {
        return targetID;
    }

    /**
     * Accessor for type.
     *
     * @return - connection type
     */
    public String getType() {
        return type;
    }

    /**
     * Accessor for cardinality String.
     *
     * @return - cardinality of an element
     */
    public String getCardinality() {
        return cardinality;
    }
}
