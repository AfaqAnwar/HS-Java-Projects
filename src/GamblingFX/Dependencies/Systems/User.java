package GamblingFX.Dependencies.Systems;

/**
 * Defines a User within the Application.
 * @Author Afaq Anwar
 * @Version 02/07/2019
 */
public class User {
    private String firstName;
    private String lastName;
    // Every User has a balance.
    private int balance;

    /**
     * Main Constructor
     * @param firstName String that represents the first name of the User.
     * @param lastName String that represents the last name of the User.
     * @param balance Integer that represents the current balance of the User.
     */
    public User(String firstName, String lastName, int balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    // Getters & Setters.
    public String getFirstName() { return firstName; }
    protected void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    protected void setLastName(String lastName) { this.lastName = lastName; }
  
    public int getBalance() { return balance; }
    protected void setBalance(int balance) { this.balance = balance; }
}