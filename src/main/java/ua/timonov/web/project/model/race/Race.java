package ua.timonov.web.project.model.race;

import ua.timonov.web.project.model.horse.Horse;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class Race {
    private Date date;
    private String location;
    private boolean isFinished;
    private List<Horse> horses;

    public Race() {
    }

    public Race(Date date, String location, boolean isFinished, List<Horse> horses) {
        this.date = date;
        this.location = location;
        this.isFinished = isFinished;
        this.horses = horses;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void setHorses(List<Horse> horses) {
        // TODO make copies
        this.horses = horses;
    }
}
