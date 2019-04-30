package SurviveTheNight.Game;
import SurviveTheNight.Entities.Consumable;
import SurviveTheNight.Entities.Entity;
import SurviveTheNight.Entities.Player;
import SurviveTheNight.Map.GameMap;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Run the text-based adventure game.
 * @Author Afaq Anwar
 * @Version 11/09/2018
 */
public class Runner {
    private static boolean gameOn = true;

    public static void main(String[] args) {
        // Initial Game Setup.
        GameMap map = new GameMap(10,10);
        Player player = new Player(0,0);
        ArrayList<Entity> items = new ArrayList<>();
        int turnNumber = 0;
        int hoursPassed = 0;
        Scanner in = new Scanner(System.in);

        // (UI / INSTRUCTIONS OUTPUT)
        System.out.println("////////////////////////////////////////////////////////" + "\n" + "You are stranded in a forest for the next 8 hours. A rescue team will pick you up at 6 AM.");
        System.out.println("For now you must explore the forest to collect and use resources in order to survive.");
        System.out.println("Be sure that you are smart about your moves, your body can only handle so much." + "\n");
        System.out.println("IMPORTANT COMMANDS:" + "\n" + "Eat [Number] ~ eat any consumable entities without picking them up.");
        System.out.println("Collect [Number] ~ collect any collectible entity.");
        System.out.println("Items ~ Show the entities you have collected. (Maximum of 5)");
        System.out.println("Use [Number] ~ Use the item in your inventory.");
        System.out.println("Drop [Number] ~ Drop the item in your inventory onto the current position.");
        System.out.println("Rest ~ Rest to regain some stamina if your stamina is at or below 0.)");
        System.out.println("////////////////////////////////////////////////////////");

        System.out.println("\n" + "You are currently at " + player.getXPos() + "," + player.getYPos() + "\n");

        // Start of the game.
        while (gameOn) {
            // Prints out the Player attributes.
            System.out.println("////////////////Current Stats////////////////");
            System.out.println("Health: " + player.getHealth());
            System.out.println("Stamina: " + player.getStamina());
            System.out.println("Nutrition: " + player.getNutrition());
            System.out.println("Hydration: " + player.getHydration());
            // Prints out the time by using the amount of hoursPassed.
            if (hoursPassed > 2) {
                System.out.println("Current Time: " + (hoursPassed - 2) + ":00 AM");
            } else {
                System.out.println("Current Time: " + (10 + hoursPassed) + ":00 PM");
            }
            System.out.println("/////////////////////////////////////////////");
            // Vital instructions are printed to remind the player of movement keys, and direct flow of the game.
            if (map.getCell(player.getXPos(),player.getYPos()).getEntitiesAmount() == 0 || turnNumber == 0) {
                System.out.println("\n" + "What will you do next?" + "\n" + "USE W, A, S ,D in order to move." + "\n" + "\n" + "ALL COMMANDS:" + "\n" + "Eat [Number], Collect [Number], Items, Use [Number], Drop [Number], Rest");
            }

            String move = in.nextLine();
            if (validMove(move, player, map, items)) {
                turnNumber++;
                System.out.println("\n" + map);
                // Checks to see if the current MapCell has an(y) Entity(ies) in it.
                if (map.getCell(player.getXPos(), player.getYPos()).getEntitiesAmount() > 0) {
                    System.out.println("\n" + "You have stumbled upon these items: ");
                    items = map.getCell(player.getXPos(), player.getYPos()).getEntitiesInCell();
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println(i + 1 + ": " + items.get(i));
                    }
                    System.out.println("\n" + "You may either collect new items, drop your old items, or keep moving." + "\n");
                }
                // Updates the hoursPassed and Player attributes (fields).
                // If the player is collecting, eating, or using any items a turn is not counted. Viewing your inventory, and resting also don't count as a "turn".
                if (!move.contains("collect") && !move.contains("eat") && !move.contains("use") && !move.contains("items") && !move.contains("rest")) {
                    hoursPassed = hourUpdate(turnNumber, hoursPassed);
                    playerStatUpdate(player);
                }
            } else {
                System.out.println("Please make a valid move.");
            }
            // Checks if the Player has won or lost the game.
            winCheck(player, hoursPassed);
        }

    }

    /**
     * Checks if the user's move is a valid move within the parameters of the map.
     * Executes the proper instructions if given a proper instruction.
     * @param move A String that contains the user's choice of movement.
     * @param p Current Player.
     * @param map Current GameMap.
     * @return true if move can be made, false is move cannot be made.
     */
    public static boolean validMove(String move, Player p, GameMap map, ArrayList<Entity> itemsAtCell) {
        // Returns are used to break the method, this will stop it from outputting unnecessary errors.

        // pos represents the Entity that will be chosen when options are given.
        int pos = -1;
        if (itemsAtCell.size() > 0) {
            // Records the Integer given after a "collect" command.
            if (move.contains("collect")) {
                try {
                    String stringPos = move.substring(7);
                    stringPos = stringPos.trim();
                    pos = Integer.parseInt(stringPos) - 1;
                    move = move.substring(0, 7);
                } catch (NumberFormatException e) {
                    System.out.println("Please choose an item based on the number it was given.");
                    return false;
                }
                // Records the Integer given after a "eat" command.
            } else if (move.contains("eat")) {
                try {
                    String stringPos = move.substring(3);
                    stringPos = stringPos.trim();
                    pos = Integer.parseInt(stringPos) - 1;
                    move = move.substring(0, 3);
                } catch (NumberFormatException e) {
                    System.out.println("Please choose an item based on the number it was given.");
                    return false;
                }
            }
            // The cell is assumed to be empty at this point.
        } else if (move.contains("eat") || move.contains("collect")) {
            System.out.println("There is nothing around you.");
            return false;
        }
        if (p.itemsInInventory() > 0) {
            // Records the Integer given after a "use" command.
            if (move.contains("use")) {
                try {
                    String stringPos = move.substring(3);
                    stringPos = stringPos.trim();
                    pos = Integer.parseInt(stringPos) - 1;
                    move = move.substring(0,3);
                }catch (NumberFormatException e) {
                    System.out.println("Please choose an item that is in your inventory.");
                    return false;
                }
                // Records the Integer given after a "drop" command.
            } else if (move.contains("drop")) {
                try {
                    String stringPos = move.substring(4);
                    stringPos = stringPos.trim();
                    pos = Integer.parseInt(stringPos) - 1;
                    move = move.substring(0,4);
                }catch (NumberFormatException e) {
                    System.out.println("Please choose an item that is in your inventory.");
                    return false;
                }
            }
            // If the Player inventory is empty and a "use" or "drop" command is used false is returned because those are invalid "moves".
        } else if (move.contains("use") && p.itemsInInventory() == 0 || move.contains("drop") && p.itemsInInventory() == 0) {
            System.out.println("Your inventory is empty.");
            return false;
        }
        // All possible cases for a move.
        // NOTE - Movement cases ("w", "a", "s", "d") can only be made if the Player stamina >= 0.
        if (p.getStamina() <= 0) {
            System.out.println("You don't have energy to keep moving. You can rest to gain some stamina back or use some items.");
        }
        switch (move) {
            case "w":
                if (p.getXPos() > 0 && p.getStamina() > 0) {
                    map.getCell(p.getXPos(), p.getYPos()).leaveCell(p);
                    map.getCell(p.getXPos() - 1, p.getYPos()).enterCell(p);
                    return true;
                } else {
                    return false;
                }
            case "d":
                if (p.getYPos() < map.getCellRow(p.getYPos()).length - 1 && p.getStamina() > 0) {
                    map.getCell(p.getXPos(),p.getYPos()).leaveCell(p);
                    map.getCell(p.getXPos(),p.getYPos() + 1).enterCell(p);
                    return true;
                }
                else {
                    return false;
                }
            case "s":
                if (p.getXPos() < map.getHeight() - 1 && p.getStamina() > 0) {
                    map.getCell(p.getXPos(), p.getYPos()).leaveCell(p);
                    map.getCell(p.getXPos() + 1, p.getYPos()).enterCell(p);
                    return true;
                } else {
                    return false;
                }
            case "a":
                if (p.getYPos() > 0 && p.getStamina() > 0) {
                    map.getCell(p.getXPos(), p.getYPos()).leaveCell(p);
                    map.getCell(p.getXPos(), p.getYPos() - 1).enterCell(p);
                    return true;
                } else {
                    return false;
                }
                // Player can recover Stamina by resting if they are at or below 0 stamina.
            case "rest" :
                if (p.getStamina() <= 0) {
                    p.setStamina(p.getStamina() + 2);
                    return true;
                } else {
                    System.out.println("You may only rest if your stamina is equal to or below 0.");
                    return false;
                }
                // Collects items in MapCell and stores them in the Player inventory.
            case "collect" :
                if (!p.isFull()) {
                    try {
                        System.out.println("You have collected a " + itemsAtCell.get(pos));
                        p.pickUp(itemsAtCell.get(pos));
                        map.getCell(p.getXPos(), p.getYPos()).removeEntitity(pos);
                        return true;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("If you would like to pick up an item please choose the proper item number.");
                        return false;
                    }
                } else {
                    System.out.println("Your inventory is full, you cannot collect anymore items.");
                    return false;
                }
                // Allows Player to eat Consumable Entities in the MapCell without collecting them.
            case "eat" :
                try {
                    ((Consumable)itemsAtCell.get(pos)).eat(p);
                    System.out.println("You ate the " + itemsAtCell.get(pos));
                    itemsAtCell.remove(pos);
                    return true;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("If you would like to pick up an item please choose the proper item number.");
                    return false;
                }
                // Prints the Player inventory.
            case "items" :
                System.out.println(p.printInventory());
                return true;
                // Use an Entity in the Player Inventory.
            case "use" :
                try {
                    ((Consumable)p.itemAtPos(pos)).eat(p);
                    System.out.println("You ate the " +  p.itemAtPos(pos) + " in your inventory.");
                    p.removeItem(pos);
                    return true;
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    System.out.println("If you would like to use an item in your inventory, please choose the proper item number.");
                    return false;
                }
                // Drop an Entity from the Player Inventory onto the current MapCell.
            case "drop" :
                try {
                    System.out.println("You have dropped a " + p.itemAtPos(pos));
                    p.drop(pos, map.getCell(p.getXPos(), p.getYPos()));
                    return true;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("If you would like to drop an item in your inventory, please choose the proper item number.");
                    return false;
                }
                default:
                    break;
        }
        // If none of the cases succeed it is assumed that the move was false.
        return false;
    }

    /**
     * Returns the update hour.
     * @param turnNumber Current Integer turn number of the game.
     * @param hoursPassed Integer Amount of hours passed.
     * @return Integer hours.
     */
    public static int hourUpdate(int turnNumber, int hoursPassed) {
        int hours = hoursPassed;
        // 6 turns are equal to an hour.
        if (turnNumber % 6 == 0) {
            hours++;
        }
        return hours;
    }

    /**
     * Updates the current Player fields.
     * @param player The current player of the game.
     */
    public static void playerStatUpdate(Player player) {
        if (player.getNutrition() <= 50 || player.getHydration() <= 50) {
            player.setHealth(player.getHealth() - 4);
            player.setStamina(player.getStamina() - 4);
            player.setNutrition(player.getNutrition() - 4);
            player.setHydration(player.getHydration() - 6);
        } else {
            player.setHealth(player.getHealth() - 1);
            player.setStamina(player.getStamina() - 2);
            player.setNutrition(player.getNutrition() - 2);
            player.setHydration(player.getHydration() - 3);
        }
    }

    /**
     * Checks to see if a player has won or lost.
     * @param player The current player of the game.
     * @param hoursPassed The current hoursPassed of the game.
     */
    public static void winCheck(Player player, int hoursPassed) {
        if (hoursPassed >= 8) {
            gameOff();
            System.out.println("You have made it past 8 hours, you were rescued by the local rescue team.");
        } else if (player.getHealth() <= 0  || player.getStamina() < 0 && player.getNutrition() < 0 && player.getHydration() < 0 && player.itemsInInventory() == 0) {
            gameOff();
            System.out.println("You have died, your body couldn't stand it anymore. You should have been smarter about your moves.");
        }
    }

    /**
     * Turns the game off by breaking the loop in the main method.
     */
    public static void gameOff() {
        gameOn = false;
    }
}
