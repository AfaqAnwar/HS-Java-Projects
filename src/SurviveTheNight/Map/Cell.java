package SurviveTheNight.Map;

import SurviveTheNight.Entities.Entity;
import SurviveTheNight.Entities.Player;
import java.util.ArrayList;

/**
 * Abstract Cell object.
 * @Author Afaq Anwar
 * @Version 11/08/2018
 */
public abstract class Cell {
    // Each Cell in our GameMap has an X and Y position.
    int xPos, yPos;
    // Each Cell in our GameMap could have items in it.
    // An ArrayList is used to represent the dynamic amount of Entities in each Cell.
    // Note - This isn't called in the constructor and is initialized without the constructor.
    // This is due to us setting the items in each of our extension Cells. (E.G - MapCell)
    ArrayList<Entity> entitiesInCell = new ArrayList<>();

    // Constructor for class fields.
    public Cell (int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * Abstract method that will allow a Player to occupy the extended Cell.
     * @param p Any Player.
     */
    public abstract void enterCell(Player p);

    /**
     * Abstract method that will allow a Player to vacate the extended Cell.
     * @param p Any Player.
     */
    public abstract void leaveCell(Player p);

    // Getters for the items in a Cell.
    public abstract void setEntitiesInCell();
    public int getEntitiesAmount() { return entitiesInCell.size(); }
    public ArrayList<Entity> getEntitiesInCell(){ return entitiesInCell; }

    /**
     * Removes an Entity from current Cell.
     * @param pos Integer value representing the position of the desired Entity to be removed inside of the ArrayList.
     */
    public void removeEntitity(int pos) {entitiesInCell.remove(pos);}
    public void addEntity(Entity entity) {entitiesInCell.add(entity);}
}
