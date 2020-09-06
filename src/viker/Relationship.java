package viker;

import java.util.ArrayList;

/**
 * Represents a Relationship object
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class Relationship {

    private ArrayList<Entity> entities;
    private String[][] cardinalities;
    private String id;
    private String relationship;

    private String type;

    /**
     * Constructs a relationship with name and unique ID.
     *
     * @param name - name of Relationship
     * @param id   - unique identifier of Relationship
     */
    public Relationship(String id, String name) {

        this.id = id;
        relationship = name;
        entities = new ArrayList<Entity>();
    }

    /**
     * Set type of relationship (strong/weak)
     *
     * @param type - relationship type
     */
    public void setRelationshipType(String type) {
        this.type = type;
    }

    /**
     * Add entities to relationship
     *
     * @param e - Entity
     */
    public void addEntity(Entity e) {
        entities.add(e);
    }

    /**
     * Get entities
     *
     * @return - entities ArrayList
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Get String output of a relationship name
     *
     * @return - relationship name
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Get type of relationship (strong/weak)
     *
     * @return - type of relationship
     */
    public String getType() {
        return type;
    }

    /**
     * Get the unique ID of a relationship
     *
     * @return - unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the cardinalities for entities based on their unique IDs
     *
     * @param rRelation - Relation as String
     * @param eRelation - End relation as String
     * @param eID       - entity ID
     */
    public void setCardinalities(String rRelation, String eRelation, String eID) {

        if (cardinalities == null) {
            cardinalities = new String[entities.size()][2];
        }

        Entity e = new Entity("", "", "");

        for (Entity e1 : entities) {
            if (e1.getId().equals(eID)) {
                e = e1;
            }
        }

        int position;
        position = entities.indexOf(e);
        cardinalities[position][0] = rRelation;
        cardinalities[position][1] = eRelation;
    }

    /**
     * Get the cardinalities of a relationship
     *
     * @return - cardinalities 2D array
     */
    public String[][] getCardinalities() {
        return cardinalities;
    }
}
