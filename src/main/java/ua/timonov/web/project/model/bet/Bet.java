package ua.timonov.web.project.model.bet;

import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.model.user.User;

import java.math.BigDecimal;

public class Bet implements Entity {
    private long id;
    private User user;
    private Odds odds;
    private BigDecimal sum;

    public Bet() {
    }

    public Bet(long id, User user, Odds odds, BigDecimal sum) {
        this.id = id;
        this.user = user;
        this.odds = odds;
        this.sum = sum;
    }

    public Bet(User user, Odds odds, BigDecimal sum) {
        this.user = user;
        this.odds = odds;
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

    public Odds getOdds() {
        return odds;
    }

    public void setOdds(Odds odds) {
        this.odds = odds;
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
        if (!odds.equals(bet.odds)) return false;
        return sum.equals(bet.sum);

    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + odds.hashCode();
        result = 31 * result + sum.hashCode();
        return result;
    }
}
