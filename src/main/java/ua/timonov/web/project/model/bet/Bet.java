package ua.timonov.web.project.model.bet;

import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.model.user.User;

import java.math.BigDecimal;

/**
 * represents Bet made by client
 */
public class Bet implements Entity {

    /* ID in database */
    private long id;

    /* client that made Bet */
    private User user;

    /* Bet rate (odds) */
    private Odds odds;

    /* bet sum of money */
    private Money sum;

    /* Bet status */
    private BetStatus betStatus;

    public Bet() {
    }

    /**
     * Builder for Bet entities
     */
    public static class Builder {
        /* requirement parameters */
        private final User user;
        private final Odds odds;

        /* optional parameters */
        private long id = 0L;
        private Money sum = new Money(BigDecimal.ZERO);
        private BetStatus betStatus = BetStatus.MADE;

        public Builder(User user, Odds odds) {
            this.user = user;
            this.odds = odds;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder betStatus(BetStatus betStatus) {
            this.betStatus = betStatus;
            return this;
        }

        public Builder money(BigDecimal sum) {
            this.sum = new Money(sum);
            return this;
        }

        public Builder money(double sum) {
            this.sum = new Money(sum);
            return this;
        }

        public Bet build() {
            return new Bet(this);
        }
    }

    public Bet(Builder builder) {
        this.user = builder.user;
        this.odds = builder.odds;
        this.id = builder.id;
        this.sum = builder.sum;
        this.betStatus = builder.betStatus;
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
        return (User) this.user.clone();
    }

    public void setUser(User user) {
        this.user = (User) user.clone();
    }

    public Odds getOdds() {
        return (Odds) this.odds.clone();
    }

    public void setOdds(Odds odds) {
        this.odds = (Odds) odds.clone();
    }

    public Money getSum() {
        return sum;
    }

    public void setSum(Money sum) {
        this.sum = sum;
    }

    public BetStatus getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(BetStatus betStatus) {
        this.betStatus = betStatus;
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
