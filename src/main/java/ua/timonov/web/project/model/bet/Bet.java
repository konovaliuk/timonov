package ua.timonov.web.project.model.bet;

import ua.timonov.web.project.model.race.Race;
import ua.timonov.web.project.model.user.User;

/**
 *
 */
public class Bet {
    private Race race;
    private User user;
    private BetType betType;
    private double rate;
    private double betSum;
    private boolean betWon;

    public Bet() {
    }

    public Bet(Race race, User user, BetType betType, double rate, double betSum, boolean betWon) {
        this.race = race;
        this.user = user;
        this.betType = betType;
        this.rate = rate;
        this.betSum = betSum;
        this.betWon = betWon;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getBetSum() {
        return betSum;
    }

    public void setBetSum(double betSum) {
        this.betSum = betSum;
    }

    public boolean isBetWon() {
        return betWon;
    }

    public void setBetWon(boolean betWon) {
        this.betWon = betWon;
    }
}
