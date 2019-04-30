package SurviveTheNight.Entities;

import SurviveTheNight.Map.Cell;

/**
 * Player Object that represents the current player.
 * Extends from Entity.
 * @Author Afaq Anwar
 * @Version 11/08/2018
 */
public class Player extends Entity {
    Entity[] inventory = new Entity[5];
    // The player will have these values assigned to them.
    int health, stamina, nutrition, hydration;

    /**
     * First constructor for each new instance of a Player.
     * @param x position of the Player along the X Axis. (Row)
     * @param y position of the Player along the Y Axis. (Column)
     * @param health integer that represents Player health.
     * @param stamina integer that represents Player stamina.
     * @param nutrition integer that represents Player nutrition.
     * @param hydration integer that represents Player hydration.
     */
    public Player (int x, int y, int health, int stamina, int nutrition, int hydration) {
        super(x, y);
        this.health = health;
        this.stamina = stamina;
        this.nutrition = nutrition;
        this.hydration = hydration;
    }

    /**
     * Second constructor for each new instance of a Player.
     * @param x position of the Player along the X Axis. (Row)
     * @param y position of the Player along the Y Axis. (Column)
     */
    public Player(int x, int y) {
        super(x, y);
        playerBuilder();
    }

    // Getters & Setters for class fields.
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public void restoreHealth(int amount) {this.health += amount;}
    public void depleteHealth(int amount) {this.health -= amount;}

    public int getStamina() { return stamina; }
    public void setStamina(int stamina) { this.stamina = stamina; }
    public void restoreStamina(int amount) {this.stamina += amount;}
    public void depleteStamina(int amount) {this.stamina -= amount;}

    public int getNutrition() { return nutrition; }
    public void setNutrition(int nutrition) { this.nutrition = nutrition; }
    public void restoreNutrition(int amount) {this.nutrition += amount;}
    public void depleteNutrition(int amount) {this.nutrition -= amount;}

    public int getHydration() { return hydration; }
    public void setHydration(int hydration) { this.hydration = hydration; }
    public void restoreHydration(int amount) {this.hydration += amount;}
    public void depleteHydration(int amount) {this.hydration -= amount;}

    /**
     * Sets attributes of a Player with random values within a specified range.
     * This helps make the game different with every play allowing for longevity, and increased fun.
     */
    public void playerBuilder() {
        this.health = (int)((Math.random() * 11) + 90);
        this.stamina = (int)((Math.random() * 16) + 85);
        this.nutrition = (int)((Math.random() * 21) + 80);
        this.hydration = (int)((Math.random() * 26) + 75);
    }


    // Inventory Methods

    /**
     * Finds the amount of items in the current Player inventory.
     * @return Integer representing the amount of items.
     */
    public int itemsInInventory() {
        int count = 0;
        for (Entity e : inventory) {
            if (e != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the Entity at a desired position.
     * **Try / Catches for IndexOutOfBounds are programmed separately.**
     * @param pos Integer representing the required position.
     * @return Entity at [pos] in the Player inventory array.
     */
    public Entity itemAtPos(int pos) {
        return inventory[pos];
    }

    /**
     * Checks if the Player inventory is full.
     * @return true or false.
     */
    public boolean isFull() {
        for (Entity e : inventory) {
            if (e == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Pick up an Entity and place it in the next available position in inventory array.
     * @param item Entity to be picked up.
     */
    public void pickUp(Entity item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                break;
            }
        }
    }

    /**
     * Remove an Entity from the Player inventory and add it to the current Cell.
     * @param pos Integer representing the position of the item in the inventory array.
     * @param cell Any extended Cell.
     */
    public void drop(int pos, Cell cell) {
        cell.addEntity(inventory[pos]);
        inventory[pos] = null;
    }

    /**
     * Remove an item from the Player inventory.
     * @param pos Integer representing the required position to be removed.
     */
    public void removeItem(int pos) {
        for (int i = 0; i < inventory.length; i++) {
            if (pos == i) {
                inventory[i] = null;
            }
        }
    }

    /**
     * Returns a string representing the inventory.
     * @return String that has information about the inventory array.
     */
    public String printInventory() {
        // This could have been much simpler, however it is coded to create a fluent text UI.
        String items = "";
        for (int i = 0; i < inventory.length; i++) {
            if (i == inventory.length - 1 && inventory[i] != null) {
                items += i + 1 + ": " + inventory[i];
            }else if (i == inventory.length - 1 && inventory[i] == null) {
                items += i + 1 + ": " + "[EMPTY]";
            }
            else if (inventory[i] == null) {
                items += i + 1 + ": " + "[EMPTY]" + ", ";
            } else {
                items += i + 1 + ": " + inventory[i] + ", ";
            }
        }
        return items;
    }
}
