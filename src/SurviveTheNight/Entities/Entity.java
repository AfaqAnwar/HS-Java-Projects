package SurviveTheNight.Entities;

import java.util.ArrayList;

/**
 * Entity Class.
 * Every object inside of the map will be considered an Entity, and obtain an entityID.
 * @Author Afaq Anwar
 * @Version 11/08/2019
 */
public class Entity {
    // ArrayList of integers that have already been assigned. This allows us to have unique IDs for each instance of an Entity.
    // Initialized to have one Integer of 0 inside, thus a method can be used to generate new IDs. [setEntityID()]
    static ArrayList<Integer> assignedEntityID = new ArrayList<Integer>() {{ add(0); }};
    // Each Entity on the GameMap will have these values assigned to it.
    int entityID;
    int xPos, yPos;

    // Constructor for class fields.
    // Since the entityID will be set later we do not need to set it here.
    public Entity(int x, int y) {
        xPos = x;
        yPos = y;
    }

    // Getters & Setters for class fields.

    /**
     * Sets the entityID to a unique number that is 1-10 values greater than the last used entityID.
     */
    public void setEntityID() {this.entityID = assignedEntityID.get(assignedEntityID.size()-1) + (int)((Math.random() * 10) + 1);}

    public int getEntityID() { return entityID; }

    public int getXPos() { return xPos; }
    public void setXPos(int xPos) { this.xPos = xPos; }

    public int getYPos() { return yPos; }
    public void setYPos(int yPos) { this.yPos = yPos; }
}
