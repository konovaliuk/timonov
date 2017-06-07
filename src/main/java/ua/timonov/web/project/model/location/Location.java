package ua.timonov.web.project.model.location;

import ua.timonov.web.project.dao.Entity;

public class Location implements Entity, Cloneable {
    private long id;
    private String name;
    private Country country;

    public Location() {
    }

    public Location(long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Location(Location location) {
        this.id = location.id;
        this.name = location.name;
        this.country = location.getCountry();
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

    public Country getCountry() {
        return (Country) this.country.clone();
    }

    public void setCountry(Country country) {
        this.country = (Country) country.clone();
    }

    @Override
    public Object clone() {
        Location clone = new Location(this);
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (!name.equals(location.name)) return false;
        return country.equals(location.country);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}
