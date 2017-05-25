package ua.timonov.web.project.model.horse;

import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.model.bet.Odds;

import java.util.ArrayList;
import java.util.List;

public class HorseInRace implements Entity {
    private long id;
    private long raceId;
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

    // TODO make copies
    public List<Odds> getOddsValues() {
        return oddsValues;
    }

    public void setOddsValues(List<Odds> oddsValues) {
        this.oddsValues = oddsValues;
    }

    public long getRaceId() {
        return raceId;
    }

    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorseInRace)) return false;

        HorseInRace that = (HorseInRace) o;

        if (id != that.id) return false;
        if (finishPlace != that.finishPlace) return false;
        if (!horse.equals(that.horse)) return false;
        return oddsValues.equals(that.oddsValues);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + horse.hashCode();
        result = 31 * result + finishPlace;
        result = 31 * result + oddsValues.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "HorseInRace{" +
                "id=" + id +
                ", horse=" + horse +
                ", finishPlace=" + finishPlace +
                ", oddsValues=" + oddsValues +
                '}';
    }
}
