package ua.timonov.web.project.model.location;

import ua.timonov.web.project.dao.Entity;

public class Location implements Entity {
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
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
