package SurviveTheNight.Map;

import SurviveTheNight.Entities.Apple;
import SurviveTheNight.Entities.Berries;
import SurviveTheNight.Entities.Mushroom;
import SurviveTheNight.Entities.Player;

/**
 * MapCell object that represents the surface of the map, or the land.
 * In essence, this is a position on the map that is empty in contents, yet can still be walked through.
 * Extends from the Cell object.
 * @Author Afaq Anwar
 * @Verison 11/14/2018
 */
public class MapCell extends Cell {
    // Every MapCell may or may not have a player.
    Player player;

    /**
     * Constructor for each new instance of a MapCell.
     * @param x desired xPos of the MapCell. (Row)
     * @param y desired yPos of the MapCell. (Column)
     */
    public MapCell(int x, int y) {
        super(x,y);
        setEntitiesInCell();
    }

    // Overriding the abstract Cell methods.
    @Override
    /**
     * Sets the items in an instance of a cell.
     * This will be called in the constructor as each MapCell must or must not have some items.
     * Instances of MapCell might be empty due to the random generation.
     */
    public void setEntitiesInCell() {
        for (int i = 0; i < 2; i++) {
            // Random Integers generated to be used as a "spawn" chance.
            int appleChance = (int)((Math.random() * 100) + 1);
            int mushroomChance = (int)((Math.random() * 100) + 1);
            int berriesChance = (int)((Math.random() * 100) + 1);
            // 30% spawn rate. (Most Common)
            if (appleChance <= 30) {
                int freshness = (int) ((Math.random() * 10) + 1);
                Apple apple = new Apple(this.xPos, this.yPos, freshness);
                this.entitiesInCell.add(apple);
            }
            // 20% spawn rate.
            if (mushroomChance <= 20) {
                // 50/50 chance to be poisonous.
                boolean poisonous = false;
                if (mushroomChance <= 10) {
                    poisonous = true;
                }
                Mushroom mushroom = new Mushroom(this.xPos, this.yPos, poisonous);
                this.entitiesInCell.add(mushroom);
            }
            // 4% spawn rate (Rarest)
            if (berriesChance <= 4) {
                // 50/50 chance to be toxic.
                boolean toxic = false;
                if (berriesChance <= 2) {
                    toxic = true;
                }
                Berries berries = new Berries(this.xPos, this.yPos, toxic);
                this.entitiesInCell.add(berries);
            }
        }
    }

    @Override
    public void enterCell(Player p) {
        player = p;
        p.setXPos(this.xPos);
        p.setYPos(this.yPos);
        System.out.println("You have walked to " + Integer.toString(xPos) + "," + Integer.toString(yPos));
    }

    @Override
    public void leaveCell(Player p) {
        player = null;
        System.out.println("You are now walking.");
    }

    @Override
    public String toString() {
        if (player != null) {
            return ("X");
        } else {
            return ("O");
        }
    }
}
