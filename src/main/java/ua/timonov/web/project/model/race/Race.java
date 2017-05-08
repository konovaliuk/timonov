package ua.timonov.web.project.model.race;

import ua.timonov.web.project.model.horse.HorseInRace;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class Race {
    private String location;
    private String country;
    private Date date;
    private List<HorseInRace> horsesInRace;

    public Race() {
    }

    public Race(String location, String country, Date date, List<HorseInRace> horsesInRace) {
        this.location = location;
        this.country = country;
        this.date = date;
        this.horsesInRace = horsesInRace;
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

    // TODO make copies
}
