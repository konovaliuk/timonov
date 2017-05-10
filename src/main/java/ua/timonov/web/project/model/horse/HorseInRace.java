package ua.timonov.web.project.model.horse;

import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;

public class HorseInRace {
    private long id;
    private Horse horse;
    private int finishPlace;
//    private Map<BetType, Odds> horseOdds;
    private BetType betType;
    private Odds odds;

    public HorseInRace() {
    }

    /*public HorseInRace(Horse horse, int finishPlace, Map<BetType, Odds> horseOdds) {
        this.horse = horse;
        this.finishPlace = finishPlace;
        this.horseOdds = horseOdds;
    }*/

    public HorseInRace(long id, Horse horse, int finishPlace, BetType betType, Odds odds) {
        this.id = id;
        this.horse = horse;
        this.finishPlace = finishPlace;
        this.betType = betType;
        this.odds = odds;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public int getFinishPlace() {
        return finishPlace;
    }

    public void setFinishPlace(int finishPlace) {
        this.finishPlace = finishPlace;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public Odds getOdds() {
        return odds;
    }

    public void setOdds(Odds odds) {
        this.odds = odds;
    }
}
