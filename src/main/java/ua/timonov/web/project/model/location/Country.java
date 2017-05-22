package ua.timonov.web.project.model.location;

import ua.timonov.web.project.dao.Entity;

public class Country implements Entity {
    private long id;
    private String name;

    public Country() {
    }

    public Country(long id, String name) {
        this.id = id;
        this.name = name;
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
}
