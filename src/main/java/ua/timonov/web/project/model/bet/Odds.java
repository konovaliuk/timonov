package ua.timonov.web.project.model.bet;

import ua.timonov.web.project.dao.Entity;

/**
 * Represents Bet rate, odds - level of possible win of Bet
 */
public class Odds implements Entity, Cloneable {

    /* entity ID */
    private long id;

    /* ID of horse in race */
    private long horseInRaceId;

    /* type of bet */
    private BetType betType;

    /* total chances that are opposite a client */
    private int total;

    /* winning chances that for a client */
    private int chances;

    /* value of winning ratio */
    private double oddsValue;

    public Odds() {
    }

    public Odds(int total, int chances) {
        this.total = total;
        this.chances = chances;
        this.oddsValue = (double) total / chances;
    }

    public Odds(long id, long horseInRaceId, BetType betType, int total, int chances) {
        this(total, chances);
        this.id = id;
        this.horseInRaceId = horseInRaceId;
        this.betType = betType;
        this.oddsValue = (double) total / chances;
    }

    public Odds(Odds odds) {
        this.id = odds.id;
        this.horseInRaceId = odds.horseInRaceId;
        this.betType = odds.betType;
        this.total = odds.total;
        this.chances = odds.chances;
        this.oddsValue = odds.oddsValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getChances() {
        return chances;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }

    public double getOddsValue() {
        return oddsValue;
    }

    public void setOddsValue(double oddsValue) {
        this.oddsValue = oddsValue;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public long getHorseInRaceId() {
        return horseInRaceId;
    }

    public void setHorseInRaceId(long horseInRaceId) {
        this.horseInRaceId = horseInRaceId;
    }

    @Override
    public Object clone() {
        return new Odds(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Odds)) return false;

        Odds odds = (Odds) o;

        if (id != odds.id) return false;
        if (total != odds.total) return false;
        if (chances != odds.chances) return false;
        return betType == odds.betType;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + betType.hashCode();
        result = 31 * result + total;
        result = 31 * result + chances;
        return result;
    }

    public String getValue() {
        return String.format("% 3d/% 3d", total, chances);
    }

    @Override
    public String toString() {
        return "Odds{" +
                "id=" + id +
                ", horseInRaceId=" + horseInRaceId +
                ", betType=" + betType +
                ", total=" + total +
                ", chances=" + chances +
                ", oddsValue=" + oddsValue +
                '}';
    }
}
