package ua.timonov.web.project.model.bet;

import ua.timonov.web.project.dao.Entity;

public class Odds implements Entity {
    private long id;
    private long horseInRaceId;
    private BetType betType;
    private int total;
    private int chances;
    private double oddsValue;

    public Odds() {
    }

    public Odds(int total, int chances) {
        this.total = total;
        this.chances = chances;
        this.oddsValue = (double) total / chances;
    }

    public Odds(long id, long horseInRaceId, BetType betType, int total, int chances) {
        this.id = id;
        this.horseInRaceId = horseInRaceId;
        this.betType = betType;
        this.total = total;
        this.chances = chances;
        this.oddsValue = (double) total / chances;
    }

    public Odds(double oddsValue) {
        this.oddsValue = oddsValue;
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
