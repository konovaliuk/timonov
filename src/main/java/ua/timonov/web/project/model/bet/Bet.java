package ua.timonov.web.project.model.bet;

import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.user.User;

public class Bet implements Entity {
    private long id;
    private User user;
    private BetType betType;
    private HorseInRace horseInRace;
    private double sum;

    public Bet() {
    }

    // TODO remove if not used
    public Bet(User user, BetType betType, HorseInRace horseInRace, double sum) {
        this.user = user;
        this.betType = betType;
        this.horseInRace = horseInRace;
        this.sum = sum;
    }

    public Bet(long id, User user, BetType betType, HorseInRace horseInRace, double sum) {
        this.id = id;
        this.user = user;
        this.betType = betType;
        this.horseInRace = horseInRace;
        this.sum = sum;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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

    public HorseInRace getHorseInRace() {
        return horseInRace;
    }

    public void setHorseInRace(HorseInRace horseInRace) {
        this.horseInRace = horseInRace;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
