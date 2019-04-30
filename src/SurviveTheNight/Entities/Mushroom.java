package SurviveTheNight.Entities;

/**
 * Mushroom Entity; can be eaten or collected.
 * @Author Afaq Anwar
 * @Version 11/09/2018
 */
public class Mushroom extends Entity implements Consumable {
    private boolean poisonous;

    /**
     * Main Constructor
     * @param x position of the Mushroom along the x Axis. (Row)
     * @param y position of the Mushroom along the y Axis. (Column)
     * @param poisonous Boolean that represents the state of the Mushroom. (Poisonous || !Poisonous)
     */
    public Mushroom(int x, int y, boolean poisonous) {
        super(x,y);
        this.poisonous = poisonous;
    }

    @Override
    /**
     * Changes Player attributes (fields) based on if Mushroom is poisonous or not.
     * Both positive & negative effects.
     */
    public void eat (Player p) {
        if (poisonous) {
            p.setHealth(p.getHealth() - 10);
            p.setStamina(p.getStamina() - 10);
            p.setNutrition(p.getNutrition() + 4);
            p.setHydration(p.getHydration() + 4);
            System.out.println("The mushroom looked funky.");
        } else {
            p.setHealth(p.getHealth() + 5);
            p.setStamina(p.getStamina() + 5);
            p.setNutrition(p.getNutrition() + 6);
            p.setHydration(p.getHydration() + 6);
            System.out.println("You got a non-poisonous mushroom.");
        }
    }

    @Override
    /**
     * Name of the Entity. (It's unknown to create a challenge for the user.)
     */
    public String toString() {
       return "Unknown Mushroom";
    }
}
