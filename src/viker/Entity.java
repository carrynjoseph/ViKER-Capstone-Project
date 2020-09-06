package viker;

import java.util.ArrayList;

/**
 * Represents an Entity object
 * <p>
 * Represents an Entity of an EER Diagram or an Entity type name of an ARM Diagram.
 * Entities have a type (weak/strong) and an Attributes object of attributes associated with them.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class Entity {

    private String id;
    private String name;
    private ArrayList<String> connections;

    private Attributes attributes;
    private String type;
    private String line;
    private String modelType;

    /**
     * Entity constructor.
     *
     * @param line - line of information per entity
     * @param type - Type of entity (strong/weak)
     */
    public Entity(String line, String type, String modelType) {

        this.line = line;
        this.type = type;
        connections = new ArrayList<>();
        this.modelType = modelType;
    }

    /**
     * Extract information for attributes from the input line. Input lines are formatted as given by an XML.
     */
    public void setUpEntity() {

        if (modelType.contains("ARM")) {

            int idIndex = line.indexOf("id=");
            int valueIndex = line.indexOf("value=");
            int styleIndex = line.indexOf("style=");
            id = line.substring(idIndex + 4, valueIndex - 2);
            name = line.substring(valueIndex + 7, styleIndex - 2);

        } else if (modelType.contains("EER")) {

            int idIndex = line.indexOf("id=");
            int valueIndex = line.indexOf("value=");
            int styleIndex = line.indexOf("style=");
            id = line.substring(idIndex + 4, valueIndex - 2);
            name = line.substring(valueIndex + 7, styleIndex - 2);
        }


    }

    /**
     * Get entity name
     *
     * @return - name of entity
     */
    public String getEntityName() {

        String entityReturn = name;
        return entityReturn;
    }

    /**
     * Get entity type
     *
     * @return - type of entity
     */
    public String getEntityType() {

        String typeReturn = type;
        return typeReturn;
    }

    /**
     * Accessor for Entity identifier.
     *
     * @return <var>id</var> Of Entity (unique)
     */
    public String getId() {
        return id;
    }

    /**
     * Get the attributes object linked to the entity - contains foreign keys, primary keys, and other attributes.
     *
     * @return - attributes object
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Adds attributes to an Attributes object and then orders them appropriately.
     *
     * @param info - Attributes info as a String
     */
    public void addAttributes(String info) {

        if (modelType.contains("EER")) {

            String[] line = info.split(", ");
            attributes = new Attributes(type, line);
            attributes.orderAttributes();

        } else if (modelType.contains("ARM")) {

            String[] line = info.split("&#xa;");
            ArrayList<String> storedAttributes = new ArrayList<>();

            for (String s : line) {
                if (!s.contains("*")) {
                    storedAttributes.add(s);
                }
            }

            String[] sortedAttributes = new String[storedAttributes.size()];

            for (int i = 0; i < sortedAttributes.length; i++) {
                sortedAttributes[i] = storedAttributes.get(i);
            }

            attributes = new Attributes(type, sortedAttributes);
            attributes.orderAttributes();
        }
    }

    /**
     * Adds a connection to the connection list
     *
     * @param s - connection to add as String
     */
    public void addConnection(String s) {
        connections.add(s);
    }

    /**
     * Get the Connections linked to the entity (other XML elements the Entity is linked to).
     *
     * @return - ArrayList of connections
     */
    public ArrayList<String> getConnections() {
        return connections;
    }
}

