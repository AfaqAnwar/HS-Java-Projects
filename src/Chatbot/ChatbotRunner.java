package Chatbot;

import java.util.Scanner;

/**
 * Initiates the Chatbot.
 * @Author Afaq Anwar
 * @Version 10/19/2018
 */
public class ChatbotRunner {
    public static void main(String[] args) {
        Chatbot chatbot = new Chatbot();
        Scanner in = new Scanner (System.in);
        System.out.println("Hello, type anything to initiate Jarvis.");
        System.out.println("If you would like to exit type 'bye'");
        String statement = in.nextLine();
        while (!statement.equalsIgnoreCase("Bye")) {
            chatbot.chatLoop(statement);
            statement = in.nextLine();
        }
    }
}
