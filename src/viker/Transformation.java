package viker;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Represents a Transformation
 * <p>
 * This is where all the extraction of information from the XML files is handled.
 * The information is taken from the input file and separated into the data structure elements.
 * Lists of the information as String output is stored for displaying complete transformations
 * when ready.
 * </p>
 *
 * @author Kouthar Dollie
 * @author Carryn Joseph
 * @author Matthew Coombe
 * @version 1.1 - 06 September 2019
 */
public class Transformation {

    private ArrayList<String> inputModel;
    private ArrayList<Entity> entities;
    private ArrayList<Relationship> relationships;
    private ArrayList<String> ARMModel;
    private String type;
    private ArrayList<Component> components = new ArrayList<>();
    private ArrayList<Connection> connections = new ArrayList<>();
    private Boolean transformationStatus;
    private ArrayList<String> EERModel;

    /**
     * Create a Transformation objects with entities, relationships, inputModel, and completed model ArrayLists.
     */
    public Transformation() {
        inputModel = new ArrayList<>();
        entities = new ArrayList<>();
        relationships = new ArrayList<>();
        ARMModel = new ArrayList<>();
        type = "";
        EERModel = new ArrayList<>();
        transformationStatus = true;

    }

    /**
     * Transform model based on contents that have been input - currently only works for EER to ARM.
     */
    public void transformModel() {
        if (type.equalsIgnoreCase("EER Diagram")) {
            System.out.println("Transforming: " + type);
            EER();
            connectAttributes();
            setRelationships();
            sortWeakEntities();
            setCardinalities();
            addEntityConnections();
            drawARM();


        } else if (type.equalsIgnoreCase("ARM Diagram")) {
            System.out.println("Transforming " + type);
            ARM();
            System.out.println("Entities stored");
            connectAttributes();
            System.out.println("Attributes connected");
            setRelationships();
            drawEER();
        }

    }

    /**
     * Store the contents of an input file as selected using the UI.
     *
     * @param filepath - Filepath for input file
     * @throws FileNotFoundException - When can't find input file
     */
    public Boolean storeContents(String filepath) throws FileNotFoundException {


        inputModel.clear();
        entities.clear();
        relationships.clear();
        ARMModel.clear();
        type = "";
        components.clear();
        connections.clear();
        File file = new File(filepath);
        //System.out.println(filepath);
        boolean storingStatus = false;
        Scanner s = new Scanner(file);
        int i = 1;
        String diagramType = "";

        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (i == 8 || i > 8) {

                if (line.contains("EER Diagram")) {
                    diagramType = "EER Diagram";
                    storingStatus = true;
                } else if (line.contains("ARM Diagram")) {
                    diagramType = "ARM Diagram";
                    storingStatus = true;
                } else {
                    inputModel.add(line);
                }

            }
            i++;


        }

        if (storingStatus == true) {
            type = diagramType;
            System.out.println(type);
            System.out.println("Read successfully");
        } else if (storingStatus == false) {
            JOptionPane.showMessageDialog(null, "Model could not be identified. Please check file format", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return storingStatus;

    }

    /**
     * Read an input model line by line (entity by entity)
     */
    public void readInputModel() {
        for (String entity : inputModel) {
            System.out.println(entity);
        }
    }

    /**
     * Sifts through an XML file to obtain relevant information and sort it into the data structure.
     * The transformation can then be done as all the information is stored in various objects.
     */
    public void EER() {
        entities.clear();
        relationships.clear();
        ARMModel.clear();

        for (int i = 0; i < inputModel.size(); i++) {
            String line = inputModel.get(i);
            if (line.contains("id") && line.contains("style=\"strokeWidth") && line.contains("value")) {
                Entity e = new Entity(line, "strong", type);
                e.setUpEntity();
                entities.add(e);
            } else if (line.contains("id") && line.contains("style=\"shape=ext;") && line.contains("value")) {
                Entity e = new Entity(line, "weak", type);
                e.setUpEntity();
                entities.add(e);
            } else if (line.contains("id") && line.contains("style=\"shape=rhombus") && line.contains("value") && line.contains("double")) {
                System.out.println("Found weak relationship");
                int idIndex = line.indexOf("id=");
                int valueIndex = line.indexOf("value=");
                int styleIndex = line.indexOf("style=");
                String id = line.substring(idIndex + 4, valueIndex - 2);
                String name = line.substring(valueIndex + 7, styleIndex - 2);
                Relationship r = new Relationship(id, name);
                r.setRelationshipType("weak");
                relationships.add(r);
                System.out.println("Weak relationship added");
            } else if (line.contains("id") && line.contains("style=\"triangle") && line.contains("value=\"ISA")) {
                int idIndex = line.indexOf("id=");
                int valueIndex = line.indexOf("value=");
                int styleIndex = line.indexOf("style=");
                String id = line.substring(idIndex + 4, valueIndex - 2);
                String name = "ISA";
                Relationship r = new Relationship(id, name);
                r.setRelationshipType("strong");
                relationships.add(r);
            } else if (line.contains("id") && line.contains("style=\"shape=rhombus") && line.contains("value")) {
                int idIndex = line.indexOf("id=");
                int valueIndex = line.indexOf("value=");
                int styleIndex = line.indexOf("style=");
                String id = line.substring(idIndex + 4, valueIndex - 2);
                String name = line.substring(valueIndex + 7, styleIndex - 2);
                Relationship r = new Relationship(id, name);
                r.setRelationshipType("strong");
                relationships.add(r);
            } else if (line.contains("id") && line.contains("style=\"ellipse") && line.contains("value=\"d")) {
                int idIndex = line.indexOf("id=");
                int valueIndex = line.indexOf("value=");
                int styleIndex = line.indexOf("style=");
                String id = line.substring(idIndex + 4, valueIndex - 2);
                String name = "Disjoint";
                Relationship r = new Relationship(id, name);
                r.setRelationshipType("strong");
                relationships.add(r);
            } else if (line.contains("id") && line.contains("style=\"ellipse") && line.contains("value")) {
                int idIndex = line.indexOf("id=");
                int valueIndex = line.indexOf("value=");
                int styleIndex = line.indexOf("style=");
                String id = line.substring(idIndex + 4, valueIndex - 2);
                String name = line.substring(valueIndex + 7, styleIndex - 2);
                String type = "";
                if (name.contains("&lt;u&gt;")) {
                    type = "primary";
                    int index = name.indexOf("&lt;/u&gt;");
                    String value = name.substring(9, index);
                    name = value;

                } else {
                    type = "other";
                }
                Component c = new Component(id, name, type);
                components.add(c);


            } else if (line.contains("source") && line.contains("target") && line.contains("endArrow=none")) {
                int sIndex = line.indexOf("source");
                int tIndex = line.indexOf("target");
                String type = "arrow";
                String sourceID = line.substring(sIndex + 8, tIndex - 2);
                String targetID = line.substring(tIndex + 8, line.lastIndexOf("\""));
                Connection con = new Connection(sourceID, targetID, type);
                connections.add(con);

            } else if (line.contains("source") && line.contains("target") && line.contains("endArrow=ER")) {
                int sIndex = line.indexOf("source");
                int tIndex = line.indexOf("target");
                int rIndex = line.indexOf("endArrow");

                String type = "relationship";
                String sourceID = line.substring(sIndex + 8, tIndex - 2);
                String targetID = line.substring(tIndex + 8, line.lastIndexOf("\""));
                String cardinality = line.substring(rIndex);

                String finalCardinality = "";
                if (!line.contains("startArrow")) {
                    finalCardinality = cardinality.substring(0, cardinality.indexOf(";"));

                } else if (line.contains("startArrow")) {
                    String endArrow = cardinality.substring(0, cardinality.indexOf(";"));
                    String startArrow = (cardinality.substring(cardinality.indexOf(";") + 1)).substring(0, cardinality.substring(cardinality.indexOf(";") + 1).indexOf(";"));
                    finalCardinality = endArrow + ", " + startArrow;
                }

                Connection con = new Connection(sourceID, targetID, type);
                con.setCardinality(finalCardinality);
                connections.add(con);
            }
            else if (line.contains("id") && line.contains("style="))
            {
                transformationStatus = false;
            }
        }
        
    }


    /**
     * Get all entities for a transformation
     *
     * @return - ArrayList of entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    
    /**
     * Get transform status
     *
     * @return - Boolean of status
     */
    public Boolean getStatus()
    {
        return transformationStatus;
    }

    /**
     * Get all relationships for a transformation
     *
     * @return - ArrayList of relationships
     */
    public ArrayList<Relationship> getRelationships() {
        return relationships;
    }

    /**
     * Get the ARM model - generated during transformation
     *
     * @return - ARMModel as ArrayList
     */
    public ArrayList<String> getARMModel() {
        return ARMModel;
    }

    /**
     * Accessor for the type of diagram inputted
     *
     * @return - type of input diagram
     */
    public String getType() {
        return type;
    }

    /**
     * Accessor for the completed transformation of EER
     *
     * @return - ArrayList of Strings representing an EER Diagram
     */
    public ArrayList<String> getEERModel() {
        return EERModel;
    }

    /**
     * Print stored data on screen - primarily for testing
     */
    public void checkStoredData() {
        for (Entity e : entities) {
            System.out.println(e.getEntityName() + " - " + e.getEntityType());
            System.out.println("Primary Keys: " + e.getAttributes().getPrimaryKeys().toString());
            System.out.println("OtherAttributes: " + e.getAttributes().getOtherAttributes().toString());
            if (e.getEntityType().equals("weak")) {
                System.out.println("Foreign Keys: " + e.getAttributes().getForeignKeys().toString());
            }
        }
    }

    /**
     * Print relationship data on screen - primarily for testing
     */
    public void checkRelationships() {
        for (Relationship r : relationships) {
            System.out.println(r.getRelationship() + " - " + r.getType());
            System.out.println("Entities: " + r.getEntities().get(0).getEntityName() + " " + r.getEntities().get(1).getEntityName());


        }
    }

    /**
     * Method to connect the different attributes to their respective entities.
     * This is needed for drawing the finished diagram on screen.
     */
    private void connectAttributes() {
        if (type.equals("EER Diagram")) {
            String info = "";
            for (Entity e : entities) {
                String eID = e.getId();
                for (Connection c : connections) {
                    if (c.getType().equals("arrow")) {
                        String source = c.getSourceID();
                        String target = c.getTargetID();
                        if (source.equals(eID)) {
                            for (Component com : components) {
                                if (target.equals(com.getId())) {
                                    if (com.getType().equals("primary")) {
                                        if (info.equals("")) {
                                            info += "^" + com.getValue();
                                        } else {
                                            info += ", ^" + com.getValue();
                                        }
                                    } else {
                                        if (info.equals("")) {
                                            info += com.getValue();
                                        } else {
                                            info += ", " + com.getValue();
                                        }
                                    }
                                }
                            }
                        } else if (target.equals(eID)) {
                            for (Component com : components) {
                                if (source.equals(com.getId())) {
                                    if (com.getType().equals("primary")) {
                                        if (info.equals("")) {
                                            info += "^" + com.getValue();
                                        } else {
                                            info += ", ^" + com.getValue();
                                        }
                                    } else {
                                        if (info.equals("")) {
                                            info += com.getValue();
                                        } else {
                                            info += ", " + com.getValue();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                e.addAttributes(info);
                info = "";
            }
        } else if (type.equals("ARM Diagram")) {
            for (Component c : components) {
                String parentId = c.getParentID();

                for (Entity e : entities) {

                    if (e.getId().equals(parentId)) {

                        e.addAttributes(c.getValue());

                    }
                }
            }

        }

    }

    /**
     * Set relationships using source and target IDs from the respective connection.
     */
    private void setRelationships() {
        if (type.contains("EER")) {
            for (Relationship r : relationships) {
                for (Connection c : connections) {
                    String sourceId = c.getSourceID();
                    String targetId = c.getTargetID();
                    if (r.getId().equals(sourceId)) {
                        for (Entity e : entities) {
                            if (e.getId().equals(targetId)) {
                                r.addEntity(e);


                            }
                        }
                    } else if (r.getId().equals(targetId)) {
                        for (Entity e : entities) {
                            if (e.getId().equals(sourceId)) {
                                r.addEntity(e);


                            }
                        }
                    }
                }
            }
        } else if (type.contains("ARM")) {

            for (Component c : components) {
                String[] line = c.getValue().split("&#xa;");
                for (String s : line) {
                    if (s.contains("*")) {
                        String id = c.getId();
                        String entity = s.substring(0, s.indexOf("*") - 1);
                        String relation = s.substring(s.indexOf("(") + 1, s.lastIndexOf(")"));


                        boolean created = false;
                        for (Relationship r : relationships) {
                            if (r.getId().equals(id) || r.getRelationship().equals(relation)) {
                                created = true;
                                for (Entity e : entities) {
                                    if (entity.equals(e.getEntityName())) {
                                        if (!r.getEntities().contains(e)) {
                                            r.addEntity(e);
                                        }
                                    }
                                }
                            }
                        }
                        if (created == false) {
                            Relationship relationship = new Relationship(id, relation);
                            relationship.setRelationshipType("strong");
                            for (Entity e : entities) {
                                if (e.getId().equals(c.getParentID())) {
                                    relationship.addEntity(e);
                                }
                            }
                            for (Entity e : entities) {
                                if (entity.equals(e.getEntityName())) {
                                    relationship.addEntity(e);
                                }
                            }
                            relationships.add(relationship);
                        }

                    }
                }
            }
        }

    }

    /**
     * Sorts the weak entities into lists that represent the foreign key for entities.
     */
    private void sortWeakEntities() {
        for (Entity e : entities) {
            if (e.getEntityType().equals("weak")) {
                for (Relationship r : relationships) {
                    if (r.getType().equals("weak")) {
                        ArrayList<Entity> es = r.getEntities();
                        if (es.contains(e)) {
                            for (Entity e2 : es) {
                                if (!e2.getId().equals(e.getId())) {
                                    Attributes a = e.getAttributes();
                                    a.addForeignKey(e2.getAttributes().getPrimaryKeys());
                                    System.out.println("Added foreign key");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Sift through relationships and their respective connections to set the cardinalities
     * of each relationship.
     */
    private void setCardinalities() {
        for (Relationship r : relationships) {
            for (Connection c : connections) {
                if (c.getType().equals("relationship")) {
                    if (r.getId().equals(c.getSourceID())) {
                        if (c.getCardinality().contains("startArrow")) {
                            int indexS = c.getCardinality().indexOf("startArrow") + 13;
                            String rRelation = c.getCardinality().substring(indexS);
                            String eRelation = c.getCardinality().substring(11, c.getCardinality().indexOf(","));

                            r.setCardinalities(rRelation, eRelation, c.getTargetID());
                        } else {
                            String rRelation = "none";
                            String eRelation = c.getCardinality().substring(11);

                            r.setCardinalities(rRelation, eRelation, c.getTargetID());
                        }
                    } else if (r.getId().equals(c.getTargetID())) {
                        if (c.getCardinality().contains("startArrow")) {
                            int indexS = c.getCardinality().indexOf("startArrow") + 13;
                            String rRelation = c.getCardinality().substring(11, c.getCardinality().indexOf(","));
                            String eRelation = c.getCardinality().substring(indexS);

                            r.setCardinalities(rRelation, eRelation, c.getSourceID());
                        } else {

                            String rRelation = "none";
                            String eRelation = c.getCardinality().substring(11);

                            r.setCardinalities(rRelation, eRelation, c.getSourceID());
                        }
                    }
                }
            }
        }
    }

    /**
     * Form connections between entities - for drawing the complete diagram.
     */
    private void addEntityConnections() {
        ArrayList<Relationship> rs = new ArrayList<>();
        for (int j = relationships.size() - 1; j > 0 || j == 0; j--) {
            rs.add(relationships.get(j));
        }

        ArrayList<Entity> line = new ArrayList<>();
        for (Relationship r : rs) {
            ArrayList<Entity> es = r.getEntities();
            if (es.size() > 1) {
                ArrayList<Entity> eReverse = new ArrayList<>();
                for (int j = es.size() - 1; j > 0 || j == 0; j--) {
                    eReverse.add(es.get(j));
                }
                for (Entity e1 : eReverse) {
                    for (Entity e2 : eReverse) {
                        if (!line.contains(e2.getEntityName())) {

                            line.add(e2);
                        }

                    }
                }
            }

        }
        String done = "";
        for (Entity e1 : line) {
            for (Relationship r : relationships) {
                if (!r.getRelationship().equals("Disjoint") && !r.getRelationship().equals("ISA")) {
                    if (r.getEntities().contains(e1)) {
                        for (Entity e2 : r.getEntities()) {
                            if (!e2.equals(e1)) {

                                if (!e1.getConnections().contains(e2.getEntityName() + " * (" + r.getRelationship() + ")")) {
                                    if (!done.contains(e2.getEntityName())) {
                                        e1.addConnection(e2.getEntityName() + " * (" + r.getRelationship() + ")");
                                    }

                                }

                            }
                        }
                    }
                }


            }
            done += e1.getEntityName();
        }

        for (Relationship r : relationships) {
            if (r.getRelationship().equals("Disjoint")) {
                ArrayList<Entity> es = r.getEntities();
                Entity e = es.get(0);
                for (Entity e1 : es) {
                    if (!e1.equals(e)) {
                        e.addConnection(e1.getEntityName() + " * (" + r.getRelationship() + ")");
                    }
                }
            }
        }

        for (Relationship r : relationships) {
            if (r.getRelationship().equals("ISA")) {
                ArrayList<Entity> es = r.getEntities();
                Entity e = es.get(0);
                for (Entity e1 : es) {
                    if (!e1.equals(e)) {
                        e1.addConnection(e.getEntityName() + " * (" + r.getRelationship() + ")");
                    }
                }
            }
        }
    }

    /**
     * First sifts through input file to obtain information, then does the transformation to EER from ARM.
     * This is NOT for the XML case - used for custom file input.
     */
    public void ARM() {
        entities.clear();
        relationships.clear();
        ARMModel.clear();
        EERModel.clear();
        transformationStatus = true;
        for (int i = 0; i < inputModel.size(); i++) {
            String line = inputModel.get(i);
            if (line.contains("id") && line.contains("value") && line.contains("style=\"swimlane")) {
                Entity e = new Entity(line, "strong", type);
                e.setUpEntity();
                entities.add(e);
            } else if (line.contains("id") && line.contains("value=") && line.contains("style=\"text") && !line.contains("self * (relationship)")) {

                int idIndex = line.indexOf("id=");
                int parentIdIndex = line.indexOf("parent=");
                int valueIndex = line.indexOf("value=");
                int styleIndex = line.indexOf("style=");
                String id = line.substring(idIndex + 4, valueIndex - 2);
                String parentId = line.substring(parentIdIndex + 8, line.lastIndexOf('\"'));
                String value = line.substring(valueIndex + 7, styleIndex - 2);

                Component c = new Component(id, value, "attributes");
                c.setParentID(parentId);
                components.add(c);

            }
            else if (line.contains("id") && !(line.contains("style=\"edgeStyle")) && !(line.contains("style=\"line")) && line.contains("value=="))
            {
                transformationStatus = false;
            }
        }

    }

    /**
     * Displays the entities in a readable format on the screen once transformation is complete.
     */
    public void drawEER() {
        EERModel.clear();

        for (Entity entity : entities) {
            String line = entity.getEntityName().trim() + " ( ";
            for (int i = 0; i < entity.getAttributes().getPrimaryKeys().size(); i++) {
                line += "^" + entity.getAttributes().getPrimaryKeys().get(i);
                if (entity.getAttributes().getOtherAttributes().size() != 0) {
                    line += ", ";
                }
            }
            for (int i = 0; i < entity.getAttributes().getOtherAttributes().size(); i++) {
                line += entity.getAttributes().getOtherAttributes().get(i);
                if (i + 1 != entity.getAttributes().getOtherAttributes().size()) {
                    line += ", ";
                }
            }
            line += " )";
            System.out.println(line);
            EERModel.add(line);
        }

        for (Relationship relationship : relationships) {
            String line = relationship.getRelationship() + " ( ";
            for (int i = 0; i < relationship.getEntities().size(); i++) {
                line += relationship.getEntities().get(i).getEntityName();
                if (i + 1 != relationship.getEntities().size()) {
                    line += " ---> ";
                }
            }
            line += " )";
            System.out.println(line);
            EERModel.add(line);
        }
    }

    public void drawARM(){
        ARMModel.clear();
        for(Entity e: entities){
        String line = e.getEntityName().trim() + " ( (self*), (";
        for(String p: e.getAttributes().getPrimaryKeys()){
            line += "^" + p + ", ";
        }
        if(e.getAttributes().getOtherAttributes() !=null){
            for(String o: e.getAttributes().getOtherAttributes()){
                if(e.getAttributes().getOtherAttributes().indexOf(o) == e.getAttributes().getOtherAttributes().size() - 1){
                    line += o;
                }
                else{
                    line += o + ", ";
                }
                
            }
        }
        //if(e.getEntityType().equals("weak")){
            for(String st: e.getConnections()){
                if(e.getConnections().indexOf(st) == e.getConnections().size() -1 ){
                    line += st;
                }
                else{
                    line += st + ",";
                }
                
               
            }
            
            line += ")";
            
        //}
        ARMModel.add(line);
        }
    }
}
