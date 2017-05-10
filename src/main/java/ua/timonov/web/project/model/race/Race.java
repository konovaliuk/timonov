package ua.timonov.web.project.model.race;

import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class Race {
    private long id;
    private RaceStatus raceStatus;
    private String location;
    private String country;
    private Date date;
    private List<HorseInRace> horsesInRace;

    public Race() {
    }

    public Race(long id, String location, String country, Date date, List<HorseInRace> horsesInRace) {
        this.id = id;
        this.location = location;
        this.country = country;
        this.date = date;
        this.horsesInRace = horsesInRace;
    }

    public Race(long id, String location, String country, Date date) {
        this.id = id;
        this.location = location;
        this.country = country;
        this.date = date;
    }

    public Race(long id, RaceStatus raceStatus, String location, String country, Date date) {
        this.id = id;
        this.raceStatus = raceStatus;
        this.location = location;
        this.country = country;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<HorseInRace> getHorsesInRace() {
        return horsesInRace;
    }

    public void setHorsesInRace(List<HorseInRace> horsesInRace) {
        this.horsesInRace = horsesInRace;
    }

    public RaceStatus getRaceStatus() {
        return raceStatus;
    }

    public void setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
    }

    // TODO make copies
}
