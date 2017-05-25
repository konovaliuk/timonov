package ua.timonov.web.project.model.bet;

import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.user.User;

import java.math.BigDecimal;

public class Bet implements Entity {
    private long id;
    private User user;
    private BetType betType;
    private HorseInRace horseInRace;
    private BigDecimal sum;

    public Bet() {
    }

    // TODO remove if not used
    public Bet(User user, BetType betType, HorseInRace horseInRace, BigDecimal sum) {
        this.user = user;
        this.betType = betType;
        this.horseInRace = horseInRace;
        this.sum = sum;
    }

    public Bet(long id, User user, BetType betType, HorseInRace horseInRace, BigDecimal sum) {
        this.id = id;
        this.user = user;
        this.betType = betType;
        this.horseInRace = horseInRace;
        this.sum = sum;
    }

    public Odds getBetOdds() {
        return horseInRace.getOddsValues()
                .stream()
                .filter(odds -> betType.equals(odds.getBetType()))
                .findFirst()
                .get();
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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bet)) return false;

        Bet bet = (Bet) o;

        if (!user.equals(bet.user)) return false;
        if (betType != bet.betType) return false;
        if (!horseInRace.equals(bet.horseInRace)) return false;
        return sum.equals(bet.sum);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + betType.hashCode();
        result = 31 * result + horseInRace.hashCode();
        result = 31 * result + sum.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", user=" + user +
                ", betType=" + betType +
                ", horseInRace=" + horseInRace +
                ", sum=" + sum +
                '}';
    }
}
