package SurviveTheNight.Entities;

/**
 * Apple Entity; can be eaten or collected.
 * @Author Afaq Anwar
 * @Version 11/09/2018
 */
public class Apple extends Entity  implements Consumable{
    private int freshness;

    /**
     * Main Constructor
     * @param x position of the Apple along the x Axis. (Row)
     * @param y position of the Apple along the y Axis. (Column)
     * @param freshness The Integer value representing the freshness of an instance of an Apple.
     */
    public Apple(int x,  int y, int freshness) {
        super(x, y);
        this.freshness = freshness;
    }

    @Override
    /**
     * Changes Player attributes (fields) based on the Apple freshness.
     * All of these changes are positive (to a certain degree).
     */
    public void eat (Player p) {
        if (freshness < 5) {
            p.restoreHealth(1);
            p.restoreStamina(1);
            p.restoreNutrition(2);
            p.restoreHydration(2);
            System.out.println("The apple seemed old.");
        } else {
            p.restoreHealth(2);
            p.restoreStamina(2);
            p.restoreNutrition(4);
            p.restoreHydration(4);
            System.out.println("You got a fresh apple.");
        }
    }

    @Override
    public String toString() {
        return ("Apple");
    }
}
