package ua.timonov.web.project.model.horse;

import ua.timonov.web.project.model.bet.BetType;
import ua.timonov.web.project.model.bet.Odds;

import java.util.Map;

public class HorseInRace {
    private Horse horse;
    private int placeAtFinish;
    private Map<BetType, Odds> horseOdds;

    public HorseInRace() {
    }

    public HorseInRace(Horse horse, int placeAtFinish, Map<BetType, Odds> horseOdds) {
        this.horse = horse;
        this.placeAtFinish = placeAtFinish;
        this.horseOdds = horseOdds;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public int getPlaceAtFinish() {
        return placeAtFinish;
    }

    public void setPlaceAtFinish(int placeAtFinish) {
        this.placeAtFinish = placeAtFinish;
    }

    public Map<BetType, Odds> getHorseOdds() {
        return horseOdds;
    }

    public void setHorseOdds(Map<BetType, Odds> horseOdds) {
        this.horseOdds = horseOdds;
    }
}
