package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.jdbc.LocationDao;
import ua.timonov.web.project.model.location.Location;

import java.util.List;

public class LocationService {

    LocationDao locationDao = new LocationDao();

    // TODO
    public List<Location> getAll() {
        return locationDao.getAll();
    }

    public Location getById(long locationId) {
        return locationDao.getById(locationId);
    }
}
