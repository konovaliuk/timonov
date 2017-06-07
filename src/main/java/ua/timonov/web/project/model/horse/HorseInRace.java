package ua.timonov.web.project.model.horse;

import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.model.bet.Odds;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents horse that participates in certain race
 */
public class HorseInRace implements Entity, Comparable<HorseInRace>, Cloneable {

    /* entity ID */
    private long id;

    /* race ID */
    private long raceId;

    /* horse */
    private Horse horse;

    /* place at finish in race */
    private int finishPlace;

    /* list of possible bet rates on this horse */
    private List<Odds> oddsValues = new ArrayList<>();

    public HorseInRace() {
    }

    public HorseInRace(long raceId, Horse horse) {
        this.raceId = raceId;
        this.horse = horse;
    }

    public HorseInRace(long id, long raceId, Horse horse, int finishPlace) {
        this(raceId, horse);
        this.id = id;
        this.finishPlace = finishPlace;
    }

    public HorseInRace(HorseInRace horseInRace) {
        this.id = horseInRace.id;
        this.raceId = horseInRace.raceId;
        this.horse = horseInRace.getHorse();
        this.finishPlace = horseInRace.finishPlace;
        this.oddsValues = horseInRace.getOddsValues();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Horse getHorse() {
        return (Horse) horse.clone();
    }

    public void setHorse(Horse horse) {
        this.horse = (Horse) horse.clone();
    }

    public int getFinishPlace() {
        return finishPlace;
    }

    public void setFinishPlace(int finishPlace) {
        this.finishPlace = finishPlace;
    }

    public List<Odds> getOddsValues() {
        List<Odds> resultList = new ArrayList<>();
        for (Odds oddsValue : this.oddsValues) {
            resultList.add((Odds) oddsValue.clone());
        }
        return resultList;
    }

    public void setOddsValues(List<Odds> oddsValues) {
        this.oddsValues = new ArrayList<>();
        for (Odds oddsValue : oddsValues) {
            this.oddsValues.add((Odds) oddsValue.clone());
        }
    }

    public long getRaceId() {
        return raceId;
    }

    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    @Override
    public Object clone() {
        return new HorseInRace(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorseInRace)) return false;

        HorseInRace that = (HorseInRace) o;

        if (raceId != that.raceId) return false;
        if (finishPlace != that.finishPlace) return false;
        if (!horse.equals(that.horse)) return false;
        return oddsValues.equals(that.oddsValues);

    }

    @Override
    public int hashCode() {
        int result = (int) (raceId ^ (raceId >>> 32));
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

    /* compares horses in race by finish place */
    @Override
    public int compareTo(HorseInRace thatHorseInRace) {
        return finishPlace - thatHorseInRace.finishPlace;
    }
}
