package ua.timonov.web.project.model.bet;

public class Odds {
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

    public Odds(double oddsValue) {
        this.oddsValue = oddsValue;
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

    @Override
    public String toString() {
        if (oddsValue == 0) {
            return String.format("%d / %d", total, chances);
        }
        return String.format("%6.3f", oddsValue);
    }
}
