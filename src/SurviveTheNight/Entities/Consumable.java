package SurviveTheNight.Entities;

/**
 * Interface for all consumable entities.
 * @Author Afaq Anwar
 * @Version 11/08/2018
 */
public interface Consumable {
    /**
     * Abstract method that will behave differently based on the consumable.
     * ~Overridden in Apple, Mushroom
     * @param player Current Player.
     */
    public void eat(Player player);

    /**
     * Abstract toString method.
     * @return A String that will be printed if the object is called in a print statement.
     */
    public String toString();
}
