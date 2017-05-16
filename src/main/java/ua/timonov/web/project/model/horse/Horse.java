package ua.timonov.web.project.model.horse;

/**
 *
 */
public class Horse {
    private long id;
    private String name;
    private int yearOfBirth;
    private int totalRaces;
    private int wonRaces;

    public Horse() {
    }

    public Horse(long id, String name, int yearOfBirth, int totalRaces, int wonRaces) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.totalRaces = totalRaces;
        this.wonRaces = wonRaces;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getTotalRaces() {
        return totalRaces;
    }

    public void setTotalRaces(int totalRaces) {
        this.totalRaces = totalRaces;
    }

    public int getWonRaces() {
        return wonRaces;
    }

    public void setWonRaces(int wonRaces) {
        this.wonRaces = wonRaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horse)) return false;

        Horse horse = (Horse) o;

        if (id != horse.id) return false;
        if (yearOfBirth != horse.yearOfBirth) return false;
        if (totalRaces != horse.totalRaces) return false;
        if (wonRaces != horse.wonRaces) return false;
        return name.equals(horse.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + yearOfBirth;
        result = 31 * result + totalRaces;
        result = 31 * result + wonRaces;
        return result;
    }
}
