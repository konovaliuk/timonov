package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.daointerface.LocationDao;
import ua.timonov.web.project.dao.daointerface.RaceDao;
import ua.timonov.web.project.model.location.Location;
import ua.timonov.web.project.model.race.Race;

public class LocationService extends DataService<Location, Race> {

    private static LocationDao locationDao = daoFactory.createLocationDao();
    private static RaceDao raceDao = daoFactory.createRaceDao();
    private static final LocationService instance = new LocationService();

    private LocationService() {
        super(locationDao, raceDao);
    }

    public static LocationService getInstance() {
        return instance;
    }
}


    /*public List<Location> getAll() {
        return locationDao.findAll();
    }

    public Location findById(long id) throws ServiceException {
        Location location = locationDao.findById(id);
        if (location == null) {
            throw new ServiceException("ID " + id + "does not exist in" + locationDao.getName());
        }
        return location;
    }*/