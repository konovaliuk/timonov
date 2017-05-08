package ua.timonov.web.project.model.bet;

import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.user.User;

/**
 *
 */
public class Bet {
    /*private BetType betType;
    private Race race;
    private User user;
    private List<Horse> betHorses;
    private Odds odds;
    private double betSum;*/

    private User user;
    private BetType betType;
    private HorseInRace betHorseInRace;
    private double betSum;

    public Bet() {
    }

    public Bet(User user, BetType betType, HorseInRace betHorseInRace, double betSum) {
        this.user = user;
        this.betType = betType;
        this.betHorseInRace = betHorseInRace;
        this.betSum = betSum;
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

    public HorseInRace getBetHorseInRace() {
        return betHorseInRace;
    }

    public void setBetHorseInRace(HorseInRace betHorseInRace) {
        this.betHorseInRace = betHorseInRace;
    }

    public double getBetSum() {
        return betSum;
    }

    public void setBetSum(double betSum) {
        this.betSum = betSum;
    }
}
