package ua.timonov.web.project.model.horse;

import ua.timonov.web.project.model.bet.Odds;

import java.util.ArrayList;
import java.util.List;

public class HorseInRace {
    private long id;
    private Horse horse;
    private int finishPlace;
    private List<Odds> oddsValues = new ArrayList<>();

    public HorseInRace() {
    }

    public HorseInRace(Long id, Horse horse, int finishPlace) {
        this.id = id;
        this.horse = horse;
        this.finishPlace = finishPlace;
    }

    public HorseInRace(long id, Horse horse, int finishPlace, List<Odds> oddsValues) {
        this.id = id;
        this.horse = horse;
        this.finishPlace = finishPlace;
        this.oddsValues = oddsValues;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Odds> getOddsValues() {
        return oddsValues;
    }

    public void setOddsValues(List<Odds> oddsValues) {
        this.oddsValues = oddsValues;
    }
}
