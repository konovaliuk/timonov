package ua.timonov.web.project.model.race;

import ua.timonov.web.project.dao.Entity;
import ua.timonov.web.project.model.horse.HorseInRace;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.user.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Race implements Entity {
    private long id;
    private RaceStatus raceStatus;
    private Location location;
    private Date date;
    private List<HorseInRace> horsesInRace = new ArrayList<>();
    private Money betSum;
    private Money paidSum;

    public Race() {
    }

    public static class Builder {
        /* requirement parameters */
        private final Location location;
        private final Date date;

        /* optional parameters */
        private long id = 0L;
        private RaceStatus raceStatus = RaceStatus.BEING_FORMED;
        private List<HorseInRace> horsesInRace = new ArrayList<>();
        private Money betSum = new Money(BigDecimal.ZERO);
        private Money paidSum = new Money(BigDecimal.ZERO);

        public Builder(Location location, Date date) {
            this.location = (Location) location.clone();
            this.date = (Date) date.clone();
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder raceStatus(RaceStatus raceStatus) {
            this.raceStatus = raceStatus;
            return this;
        }

        public Builder horsesInRace(List<HorseInRace> horsesInRace) {
            this.horsesInRace = new ArrayList<>();
            for (HorseInRace horseInRace : horsesInRace) {
                this.horsesInRace.add((HorseInRace) horseInRace.clone());
            }
            return this;
        }

        public Builder betSum(double sum) {
            this.betSum = new Money(sum);
            return this;
        }

        public Builder betSum(BigDecimal sum) {
            this.betSum = new Money(sum);
            return this;
        }

        public Builder paidSum(double sum) {
            this.paidSum = new Money(sum);
            return this;
        }

        public Builder paidSum(BigDecimal sum) {
            this.paidSum = new Money(sum);
            return this;
        }

        public Race build() {
            return new Race(this);
        }
    }

    public Race(Builder builder) {
        this.id = builder.id;
        this.raceStatus = builder.raceStatus;
        this.location = builder.location;
        this.date = builder.date;
        this.horsesInRace = builder.horsesInRace;
        this.betSum = builder.betSum;
        this.paidSum = builder.paidSum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Location getLocation() {
        return (Location) this.location.clone();
    }

    public void setLocation(Location location) {
        this.location = (Location) location.clone();
    }

    public Date getDate() {
        return (Date) this.date.clone();
    }

    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }

    public Money getBetSum() {
        return betSum;
    }

    public void setBetSum(Money betSum) {
        this.betSum = betSum;
    }

    public Money getPaidSum() {
        return paidSum;
    }

    public void setPaidSum(Money paidSum) {
        this.paidSum = paidSum;
    }

    public List<HorseInRace> getHorsesInRace() {
        List<HorseInRace> resultList = new ArrayList<>();
        for (HorseInRace horseInRace : this.horsesInRace) {
            resultList.add((HorseInRace) horseInRace.clone());
        }
        return resultList;
    }

    public void setHorsesInRace(List<HorseInRace> horsesInRace) {
        this.horsesInRace = new ArrayList<>();
        for (HorseInRace horseInRace : horsesInRace) {
            this.horsesInRace.add((HorseInRace) horseInRace.clone());
        }
    }

    public RaceStatus getRaceStatus() {
        return raceStatus;
    }

    public void setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Race)) return false;

        Race race = (Race) o;

        if (raceStatus != race.raceStatus) return false;
        if (!location.equals(race.location)) return false;
        if (!date.equals(race.date)) return false;
        return horsesInRace.equals(race.horsesInRace);

    }

    @Override
    public int hashCode() {
        int result = raceStatus.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + horsesInRace.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", raceStatus=" + raceStatus +
                ", location=" + location +
                ", date=" + date +
                ", horsesInRace=" + horsesInRace +
                '}';
    }
}
