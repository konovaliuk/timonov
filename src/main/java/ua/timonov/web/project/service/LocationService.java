package ua.timonov.web.project.service;

import ua.timonov.web.project.dao.Dao;
import ua.timonov.web.project.dao.daointerface.LocationDao;
import ua.timonov.web.project.model.location.Location;

public class LocationService extends DataService<Location> {

    private static LocationDao locationDao = daoFactory.createLocationDao();
    private static final LocationService instance = new LocationService(locationDao);

    private LocationService(Dao<Location> locationDao) {
        super(locationDao, "Location");
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