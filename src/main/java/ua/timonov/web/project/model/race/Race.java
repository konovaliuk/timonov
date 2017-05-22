package ua.timonov.web.project.model.race;

import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.location.Location;

import java.util.Date;
import java.util.List;

public class Race implements Entity {
    private long id;
    private RaceStatus raceStatus;
    private Location location;
    private Date date;
    private List<HorseInRace> horsesInRace;

    public Race() {
    }

    public Race(long id, Location location, Date date, List<HorseInRace> horsesInRace) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.horsesInRace = horsesInRace;
    }

    public Race(long id, Location location, Date date) {
        this.id = id;
        this.location = location;
        this.date = date;
    }

    public Race(long id, RaceStatus raceStatus, Location location, Date date) {
        this.id = id;
        this.raceStatus = raceStatus;
        this.location = location;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
