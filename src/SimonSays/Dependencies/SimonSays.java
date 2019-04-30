package SimonSays.Dependencies;

import java.util.ArrayList;

/**
 * Simon Says.
 * @Author Afaq Anwar
 * @Version 01/05/2019
 */
public class SimonSays {
    private ArrayList<String> pattern;
    private final String[] options = { "Green", "Red", "Yellow", "Blue"};
    private final int STARTAMOUNT = 1;
    private int currentIndex;
    private int score;
    private boolean gameOn;

    /**
     * Main Constructor, initializes all necessary variables.
     */
    public SimonSays() {
        currentIndex = 0;
        score = 0;
        pattern = new ArrayList<>();
        generateRandomPattern();
        gameOn = true;
    }

    /**
     * @return ArrayList of Strings that represents the current pattern.
     */
    public ArrayList<String> getPattern() { return this.pattern; }

    /**
     * @return Integer that represents the current index (progress) of the current pattern.
     */
    public int getCurrentIndex() { return this.currentIndex; }

    /**
     * @return Integer that represents the "Score".
     */
    public int getScore() { return this.score; }

    /**
     * Generate a random pattern based upon the given STARTAMOUNT.
     * In this case it will just generate one Color upon start.
     */
    private void generateRandomPattern() {
        for (int i = 0; i < STARTAMOUNT; i++) {
            pattern.add(options[(int)(Math.random() * options.length)]);
        }
    }

    /**
     * Adds a random color to the current pattern.
     * May produce an easy pattern at times. (E.G - Yellow, Yellow, Yellow)
     */
    private void addToPattern() {
        pattern.add(options[(int)(Math.random() * options.length)]);
    }

    /**
     * Checks if the input (String) passed it equal to the color at the currentIndex.
     * If true and the last index has not been reached then the index increases.
     * If true and the last index has been reached a new color is added and the score goes up.
     * Else the game ends due to the user inputting the incorrect color.
     * @param input String that represents the current color chosen.
     * @return true or false.
     */
    public boolean checkInput(String input) {
        if (pattern.get(currentIndex).equals(input)) {
            if (currentIndex == (pattern.size() - 1)) {
                addToPattern();
                currentIndex = 0;
                score++;
                return true;
            }
            currentIndex++;
            return true;
        } else {
            endGame();
            return false;
        }
    }

    /**
     * End the game.
     */
    public void endGame() {
        pattern.clear();
        currentIndex = 0;
        gameOn = false;
    }
}
