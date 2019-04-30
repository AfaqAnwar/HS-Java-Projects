package GuessingGame;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * A text program that allows the user to guess the computer's selected number &
 * the computer to guess the user's number.
 * @Author Afaq Anwar
 * @Version 11/14/2018
 */
public class GuessingGame {
    /***
     * This main method initializes the program. (Void Method because it should not return anything.)
     * Asks the user which game they would like to play.
     *
     *  Notice the try & catch statements. This is to account for the incapability of the user to follow directions.
     *  Tries to ask for an input again if a false input was submitted.
     *  Catches any input mismatch; ex - entering an integer, or anything besides the given option strings.
     *
     *  Runs the game chosen depending on user input, ignoring the case, however it is space sensitive. This could potentially be fixed by reading the first word.
     */
    public static void main(String[] args) {
        System.out.println("Which game would you like to play?" + "\n" + "Guess the Number or Number Guesser?");
        Scanner gameInput = new Scanner(System.in);
        String gameChoice = gameInput.nextLine();

        while (!gameChoice.equalsIgnoreCase("Guess the Number") && !gameChoice.equalsIgnoreCase("Number Guesser")) {
            try {
                System.out.println("Choose a game, please.");
                gameChoice = gameInput.nextLine();
            } catch (InputMismatchException exception) {
                gameChoice = gameInput.nextLine();
            }
        }

        if (gameChoice.equalsIgnoreCase("Guess the Number")) {
            guessNumber();
        } else if (gameChoice.equalsIgnoreCase("Number Guesser")) {
            numberGuesser();
        }
        gameInput.close();
    }

    /***
     * This is the "Guess the Number" game. (Void Method because it should not return anything.)
     * The CPU chooses a number based on the difficulty level chosen by the user at the start of the game.
     * The amount of guesses inputted by the user is also tracked.
     */
    public static void guessNumber() {
        // The first 5 lines get the range from the difficulty() method and parse the returned string into each respective variable.
        String range = difficulty();
        String lowString = range.substring(0, range.indexOf("-"));
        int lowerBound = Integer.parseInt(lowString);
        String highString = range.substring(range.indexOf("-") + 1);
        int upperBound = Integer.parseInt(highString);

        int randomNumber = (int)((Math.random() * upperBound - lowerBound + 1) + lowerBound);
        int userGuesses = 1;

        System.out.println("\n" + "--------------" + "\n");
        System.out.println("Attempt to guess the number I'm thinking of between " + lowerBound + " - " + (upperBound) + " (Inclusive).");
        System.out.println("\n" + "You have an unlimited amount of attempts.");

        Scanner userInput = new Scanner(System.in);
        int userNumber = userInput.nextInt();

        while (userNumber != randomNumber) {
            userGuesses++;
            if (userNumber > randomNumber) {
                System.out.println("Too High...");
            } else if (userNumber < randomNumber) {
                System.out.println("Too Low...");
            }
            System.out.println("\n" + "keep trying..." + "\n");
            userNumber = userInput.nextInt();
        }

        System.out.println("You are right! The number I chose was " + randomNumber + "\n");
        System.out.println("It took you " + userGuesses + " attempts");
        userInput.close();
    }

    /***
     * This is the "Number Guesser" game. (Void Method because it should not return anything.)
     *
     * The CPU attempts to guess the user's number using the binary search method.
     *  This is done by taking averages of the upper and lower bounds.
     *  If the user number is lower than the guess, it will average one below the current guess & the lowerBound.
     *  If the user number is higher than the guess, it will average one above the current guess & the upperBound.
     *  This method allows for the cpu average to lean to the left side of the scale, thus making it efficient.
     *
     * The Scanner input must be started before the while loop, therefore the user can input anything to start the game.
     * Try & Catch cases are used to make sure the user inputs a string.
     * If the CPU reaches the last number, it will keep asking the user if it correct, until he or she replies with "correct".
     */
    public static void numberGuesser() {
        // The first 5 lines get the range from the difficulty() method and parse the returned string into each respective variable.
        String range = difficulty();
        String lowString = range.substring(0, range.indexOf("-"));
        int lowerBound = Integer.parseInt(lowString);
        String highString = range.substring(range.indexOf("-") + 1);
        int upperBound = Integer.parseInt(highString);

        int cpuNum = (lowerBound + upperBound) / 2;
        int cpuGuesses = 1;

        System.out.println("\n" + "--------------" + "\n");
        System.out.println("I will attempt to guess the number you're thinking of between " + lowerBound + " - " + upperBound + "\n");
        System.out.println("Type lower, higher, or correct depending on my guess." + "\n");
        System.out.println("Type anything to start.");

        Scanner userInput = new Scanner(System.in);
        String userAnswer = userInput.next();

        while (!userAnswer.equalsIgnoreCase("correct")) {
            while (userAnswer instanceof String == false) {
                try {
                    System.out.println("Please provide a valid answer.");
                    userAnswer = userInput.next();
                } catch (InputMismatchException exception) {
                    userAnswer = userInput.next();
                }
            }
            System.out.println("Is your number " + cpuNum + "?" + " Or is it lower or higher?");
            userAnswer = userInput.next();
            if (userAnswer.equalsIgnoreCase("lower")) {
                upperBound = cpuNum - 1;
                cpuNum = (lowerBound + upperBound) / 2;
            } else if (userAnswer.equalsIgnoreCase("higher")) {
                lowerBound = cpuNum + 1;
                cpuNum = (lowerBound + upperBound) / 2;
            }
            cpuGuesses++;
        }

        System.out.println("\n" + "Your number was " + cpuNum + ".");
        System.out.println("It took me " + cpuGuesses + " tries");

        userInput.close();
    }

    /***
     * This method is executed before the start of each game.
     * Allows the user to select their number range.
     * @return String the has the range, this will be parsed in one of the game methods.
     */
    public static String difficulty() {
        System.out.println("Please choose your difficulty level.");
        System.out.println("Type 1 for numbers 1-10");
        System.out.println("Type 2 for numbers 1-100");
        System.out.println("Type 3 for numbers 1-1000");
        System.out.println("Type 4 for numbers 1-10000");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
            try {
                System.out.println("Please choose your difficulty.");
                choice = input.nextInt();
            } catch (InputMismatchException exception) {
                choice = input.nextInt();
            }
        }
        if (choice == 1) {
            return "1-10";
        } else if (choice == 2) {
            return "1-100";
        } else if (choice == 3) {
            return "1-1000";
        } else if (choice == 4) {
            return  "1-10000";
        } else {
            return null;
        }
    }
}