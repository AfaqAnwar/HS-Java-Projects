package SurviveTheNight.Entities;

/**
 * Berries Entity; can be eaten or collected.
 * @Author Afaq Anwar
 * @Version 11/09/2018
 */
public class Berries extends Entity implements Consumable {
    private boolean toxic;

    /**
     * Main constructor
     * @param x position of the Berries along the x Axis. (Row)
     * @param y position of the Berries along the y Axis. (Column)
     * @param toxic Boolean that represents the state of the Berries. (Toxic || !Toxic)
     */
    public Berries (int x, int y, boolean toxic) {
        super(x,y);
        this.toxic = toxic;
    }

    @Override
    /**
     * Changes Player attributes (fields) based on if the Berries are toxic or not.
     * Has EXTREME Positive & Negative effects.
     */
    public void eat (Player player) {
        if (toxic) {
            player.setHealth(player.getHealth() - 50);
            player.setStamina(player.getStamina() - 50);
            player.setNutrition(player.getNutrition() + 1);
            player.setHydration(player.getHydration() + 1);
            System.out.println("The berries had a weird smell.");
        } else {
            player.setHealth(player.getHealth() + 30);
            player.setStamina(player.getStamina() + 30);
            player.setNutrition(player.getNutrition() + 5);
            player.setHydration(player.getHydration() + 5);
            System.out.println("You got lucky and found non-toxic berries.");
        }
    }

    @Override
    /**
     * Name of the Entity. (It's unknown to create a challenge for the user.)
     */
    public String toString() {
        return "Unknown Rare Berries";
    }
}
