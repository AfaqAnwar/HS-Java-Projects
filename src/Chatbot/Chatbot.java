package Chatbot;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A NYC event chatbot, designed to give the name of some events based on interests.
 * Also has some "general" intelligence.
 * @Author Afaq Anwar
 * @Version 10/18/2018
 *
 * @Edited 04/30/2019
 * Eventbrite changed their website layout, thus this project was slightly altered.
 * In the case that such an event occurs in the future, the website url query and css element scraped can easily be replaced.
 */
public class Chatbot {
    /**
     * Runs the conversation for this particular chatbot (Jarvis the Entertainment Bot).
     * Also builds the Array Lists for the given files. This allows us to add to Jarvis' vocabulary on the fly.
     * @param statement the statement typed by the user
     */
    public void chatLoop(String statement) {
        arrayListBuilder(generalGreetings, "src/Chatbot/generalGreetings.txt");
        arrayListBuilder(generalResponses, "src/Chatbot/generalResponses.txt");
        arrayListBuilder(generalPolarResponses, "src/Chatbot/generalPolarResponses.txt");
        arrayListBuilder(generalQuestionsLocation, "src/Chatbot/generalQuestionsLocation.txt");
        arrayListBuilder(generalQuestionsInterest, "src/Chatbot/generalQuestionsInterest.txt");
        arrayListBuilder(generalConversationStarters, "src/Chatbot/generalConversationStarters.txt");
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome! My name is Jarvis, I'll assist you with finding things to do around NYC.");
        System.out.println(getGreeting());
        while (!statement.equalsIgnoreCase("Bye")) {
            statement = in.nextLine();
            addToUserResponses(statement);
            System.out.println(getResponse(statement));
        }
    }

    /**
     * This is just a simple method returning a pre-generated message.
     * @return The first message the computer sends.
     */
    public String getGreeting() {
        return "I hope you're fine today. I'm in Brooklyn, where are you?";
    }

    /**
     * Get's a response from the computer based on the situation. Most situations are accounted for, however without machine learning
     * not every single situation can be accounted for without a million if statements, therefore the Array Lists help the program
     * become slightly dynamic.
     *
     * The Computer can respond to questions through a set of predefined responses.
     * The Computer can respond to unknown answers and direct the conversation back to the topic through a set of predefined questions.
     * The Computer can respond to empty / invalid answers and ask questions to fill in the needed properties of userLocation & userInterest.
     *
     * @param statement the statement typed by the user, in this case it is the parameter given in chatLoop().
     * @return response that is changed depending on the situation.
     */
    public String getResponse(String statement) {
        String response = "";
        /* General Communicative responses.
           Responds to greetings by finding a greeting that is stored in generalGreetings.txt, then replying with a random greeting.
           Responds to questions by finding the "?" in the string.
        */
        if (statement.length() == 0) {
            response = "Sorry, I didn't get that. What did you want to say?";
        } else if (generalGreetings.stream().anyMatch(statement::equalsIgnoreCase)) {
            response = generalGreetings.get(randomGenerator(generalGreetings.size(), 0));
        } else if (statement.indexOf("?") == statement.length() - 1) {
            response = generalPolarResponses.get(randomGenerator(generalPolarResponses.size(), 0));
        }
        /*
            Responds to location of the user, this accounts for all case scenarios. Initializes the flow of the conversation.
            Asks for the user's location, specifically a borough in New York.
            Through the use of ArrayList generalQuestionsLocation a sense of "intelligence" is presented by asking the question differently.
            This is an else if statement to allow jarvis to have a fluent conversation / respond to statements at the beginning without forcing the user to give their location.
            ** The "&& confirmed != -1" parameter was added to allow the user to carry a normal conversation, and only restart once the keyword "help" is found.
        */
        else if (findKeyword(statement, "I'm in", 0) >= 0 && confirmed != -1) {
            userLocation = transformStatement(statement, "I'm in", 0);
            if (isInStringArray(boroughs, userLocation) == 1) {
                response = "Oh you're in " + userLocation + ". Great well let me help you find something to do today!" + "\n" + "What are you interested in?";
            } else {
                response = "That's not in New York, I'm sorry I don't know about " + userLocation + " yet.";
                // userLocation is reset to null to avoid program errors.
                userLocation = null;
            }
        } else if (findKeyword(statement, "Im in", 0) >= 0 && confirmed != -1) {
            userLocation = transformStatement(statement, "Im in", 0);
            if (isInStringArray(boroughs, userLocation) == 1) {
                response = "Oh you're in " + userLocation + ". Great well let me help you find something to do today!" + "\n" + "What are you interested in?";
            } else {
                response = "That's not a borough in New York. I'm sorry I don't know about " + userLocation + " yet." + "\n" + "Please let me know which borough you live in.";
                userLocation = null;
            }
        } else if (isInStringArray(boroughs, statement) == 1 && confirmed != -1) {
            userLocation = statement;
            response = "Oh you're in " + statement + ". Great well let me help you find something to do today!" + "\n" + "What are you interested in?";
        } else if (userLocation == null && userResponses.size() > 0) {
            response = generalQuestionsLocation.get(randomGenerator(generalQuestionsLocation.size(), 0));
        }
        // Jarvis' generic responses lie here, if he does not know something he will reply to it using any of the statements found in generalResponses.txt.
        else {
            response = generalResponses.get(randomGenerator(generalResponses.size(), 0));
        }
        /*
            Attempts to get an interest from the user, and then scrapes a web page using eventGenerator().
            Asks questions about the user's interest.
            Gives a sense of "intelligence" by asking differently phrased questions through the use of ArrayList generalQuestionsInterests.
        */
        if (userLocation != null && userInterest == null && confirmed == 0) {
            // int Confirmed was used to direct the flow of the program back and fourth if needed.
            /*
                E.G. - User chooses a topic to then be used for event searches, however decides they don't like it.
                confirmed will go back to 1, then the Computer will ask for another interest.
                When confirmed = 0 the user is going to enter the interest loop. This is just the entry stage.
                When confirmed = 1 user needs to say & confirm their choice about their interest.
                When confirmed = 2 user's interest is searched for.
            */
            confirmed = 1;
        } else if (userLocation != null && confirmed == 1) {
            // Regex statement checks for characters from the English Alphabet & if necessary a space in between words. (Disables user from inputting an empty string or a string comprised of numbers only)
            if (statement.matches("[a-zA-z\\s]+")) {
                userInterest = statement;
                response = "Oh ok, so you're interested in " + userInterest + ", right?";
                confirmed = 2;
            } else {
                // Sends the user back into this loop by setting confirmed to 1.
                response = "I'm sorry, I have no idea what that is." + "\n" + "Let me phrase it differently for you." + "\n" + generalQuestionsInterest.get(randomGenerator(generalQuestionsInterest.size(), 0));
                confirmed = 1;
            }
        } else if (confirmed == 2) {
            if (statement.equalsIgnoreCase("yes")) {
                eventGenerator();
                userInterest = null;
                // Confirmed is set to -1 to allow for the user to continue a conversation with Jarvis until the "help" keyword is found.
                confirmed = -1;
                response = "\n" + "Woah that was cool right? If you don't like any of these you could always start over, just make sure to type for my help!";
            }
            else if (statement.equalsIgnoreCase("no")) {
                userInterest = null;
                confirmed = 1;
                response = generalQuestionsInterest.get(randomGenerator(generalQuestionsInterest.size(), 0));
            }
            else {
                response = "Sorry what did you say? Are you interested in " + userInterest + "?";
            }
            /*
               After the initial search has been completed, the "help" keyword allows the user to restart the process by resetting user location & interest.
               This is to allow the user to have a conversation with the bot, if they wish to.
            */
        } else if (findKeyword(statement, "help", 0) >= 0) {
            response = "Do you need help? I can help you find events around NYC. Which borough are you in?";
            userLocation = null;
            userInterest = null;
            confirmed = 0;
        }
        return  response;
    }

    /**
     * Adds the user input to an Array List (userResponses).
     * @param statement the statement to be added into the Array List.
     */
    public void addToUserResponses(String statement) {
        userResponses.add(statement);
    }

    /**
     * Method that checks if a given array has a string.
     * @param array the desired array to be checked.
     * @param string the string being searched for.
     * @return 1 if the string is in the array, 0 if the string is not in the array.
     */
    public int isInStringArray(String array[], String string) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(string)){
                return 1;
            }
        }
        return 0;
    }

    /**
     * Dynamic transformMethod that removes part of string.
     * @param statement string that needs to be altered.
     * @param keyword string that needs to be removed.
     * @param startPos starting index to be searched for.
     * @return altered statement without the keyword.
     */
    private String transformStatement(String statement, String keyword, int startPos)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement.length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement.length() - 1);
        }
        int psn = findKeyword(statement, keyword, startPos);
        String restOfStatement = statement.substring(psn + keyword.length()).trim();
        return restOfStatement;
    }

    /**
     * Creates an Array List object based on a text file.
     * @param name the name of the Array List that has already be initialized.
     * @param fileName the name of the text file that is stored inside of the project folder.
     */
    public void arrayListBuilder(ArrayList name, String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            for (int i = 0; line != null; i++) {
                name.add(i, line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * A random number generator.
     * @param max the maximum number (exclusive.)
     * @param min the minimum number (inclusive.)
     * @return a random number between min, max.
     */
    public int randomGenerator (int max, int min) {
        return ((int)((Math.random() * max - min) + min));
    }

    /**
     * Dymanic element scraper. Takes a ceratain css element from a webpage, adds it/them into an Array List.
     * @param url the url of the website.
     * @param cssElement the css element needed to be scraped.
     * @param selectedArrayList the Array List that will contain the text of the css element(s).
     */
    public void webElementScraper(String url, String cssElement, ArrayList selectedArrayList) {
        // Attempts to connect to the given web url.
        try {
            // Creates a new Document object.
            Document doc = Jsoup.connect(url).get();
            // Transfers all selected css elements into an Elements object.
            Elements divTitles = doc.select(cssElement);
            // Loops through each element, and adds the text from each element to our Array List.
            for (Element divText: divTitles) {
                selectedArrayList.add(divText.text());
            }
            // Catches a website connection error.
        } catch (IOException e) {
            System.out.println("Uh oh something went wrong...");
        }
    }

    /**
     * Generates the events using our dynamic web scraper (webElementScraper).
     * Uses www.eventbrite.com for information pertaining to events around the user's location.
     * Manipulates the url of the website to create a document based on the custom search.
     * Prints all of the events in the Array List events.
     * (This was given a 0.5 second delay between each listing to give some effect of artificial intelligence.)
     */
    public void eventGenerator() {
        events.clear();
        userLocation = userLocation.trim();
        userInterest = userInterest.trim();
        userLocation = userLocation.replace(" ", "-");
        userInterest = userInterest.replace(" ", "-");
        webElementScraper("https://www.eventbrite.com/d/ny--borough-of-" + userLocation + "/events--today/" + userInterest + "/?page=1", "div.eds-is-hidden-accessible", events);
        System.out.println("Based on your interest of " + userInterest + " here are some events I found around " + userLocation + " today...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        for (String e : events) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("\n" + e);
        }
    }

    // All the variables, arrays, and Array Lists that needed to be initialized beforehand.
    String boroughs[] = new String[] {"Manhattan", "Brooklyn", "Queens", "Bronx", "Staten Island"};
    String userLocation = null;
    String userInterest = null;
    ArrayList<String> events = new ArrayList<>();
    ArrayList<String> userResponses = new ArrayList<>();
    ArrayList<String> generalGreetings = new ArrayList<>();
    ArrayList<String> generalResponses = new ArrayList<>();
    ArrayList<String> generalPolarResponses = new ArrayList<>();
    ArrayList<String> generalQuestionsLocation = new ArrayList<>();
    ArrayList<String> generalQuestionsInterest = new ArrayList<>();
    ArrayList<String> generalConversationStarters = new ArrayList<>();
    int confirmed = 0;


    // Pre-provided Methods
    /**
     * Search for one word in phrase. The search is not case
     * sensitive. This method will check that the given goal
     * is not a substring of a longer string (so, for
     * example, "I know" does not contain "no").
     *
     * @param statement
     *            the string to search
     * @param goal
     *            the string to search for
     * @param startPos
     *            the character of the string to begin the
     *            search at
     * @return the index of the first occurrence of goal in
     *         statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal, int startPos)
    {
        String phrase = statement.trim().toLowerCase();
        goal = goal.toLowerCase();

        // The only change to incorporate the startPos is in
        // the line below
        int psn = phrase.indexOf(goal, startPos);

        // Refinement--make sure the goal isn't part of a
        // word
        while (psn >= 0)
        {
            // Find the string of length 1 before and after
            // the word
            String before = " ", after = " ";
            if (psn > 0)
            {
                before = phrase.substring(psn - 1, psn);
            }
            if (psn + goal.length() < phrase.length())
            {
                after = phrase.substring(
                        psn + goal.length(),
                        psn + goal.length() + 1);
            }

            // If before and after aren't letters, we've
            // found the word
            if (((before.compareTo("a") < 0) || (before
                    .compareTo("z") > 0)) // before is not a
                    // letter
                    && ((after.compareTo("a") < 0) || (after
                    .compareTo("z") > 0)))
            {
                return psn;
            }

            // The last position didn't work, so let's find
            // the next, if there is one.
            psn = phrase.indexOf(goal, psn + 1);

        }

        return -1;
    }

    /**
     * Search for one word in phrase.  The search is not case sensitive.
     * This method will check that the given goal is not a substring of a longer string
     * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.
     * @param statement the string to search
     * @param goal the string to search for
     * @return the index of the first occurrence of goal in statement or -1 if it's not found
     */
    private int findKeyword(String statement, String goal)
    {
        return findKeyword (statement, goal, 0);
    }
}
