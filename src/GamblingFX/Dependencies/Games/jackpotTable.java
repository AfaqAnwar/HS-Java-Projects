package GamblingFX.Dependencies.Games;

import javafx.beans.property.SimpleStringProperty;

public class jackpotTable
{
    private SimpleStringProperty name, bet, percent;

    public jackpotTable(String name, String bet, String percent)
    {
        this.name = new SimpleStringProperty(name);
        this.bet = new SimpleStringProperty(bet);
        this.percent = new SimpleStringProperty(percent);
    }

    public String getName()
    {
        return name.get();
    }
    public void setName(SimpleStringProperty name)
    {
        this.name = name;
    }

    public String getBet()
    {
        return bet.get();
    }
    public void setBet(SimpleStringProperty bet)
    {
        this.bet = bet;
    }

    public String getPercent()
    {
        return percent.get();
    }
    public void setPercent(SimpleStringProperty percent)
    {
        this.percent = percent;
    }
}
